// src/main/java/utnfc/isi/back/sim/service/ImportService.java
package utnfc.isi.back.sim.service;

import utnfc.isi.back.sim.csv.CsvGameRow;
import utnfc.isi.back.sim.domain.*;
import utnfc.isi.back.sim.infra.LocalEntityManagerProvider;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * Importa filas de CSV en dos fases: 1) Parseo y acumulación en memoria (Maps),
 * 2) Persistencia en una única transacción: primero maestras, luego juegos.
 */
public class ImportService {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class ImportResult {

        private int filasLeidas;
        private int filasValidas;
        private int desarrolladoresInsertados;
        private int generosInsertados;
        private int plataformasInsertadas;
        private int juegosInsertados;
    }

    // --- API principal ---
    public ImportResult importar(List<CsvGameRow> rows) {
        Objects.requireNonNull(rows, "rows");

        // 1) Acumulación en memoria
        Map<String, Desarrollador> devs = new LinkedHashMap<>();
        Map<String, Genero> gens = new LinkedHashMap<>();
        Map<String, Plataforma> plats = new LinkedHashMap<>();
        List<PendingGame> juegosPendientes = new ArrayList<>();

        int validas = 0;
        for (var r : rows) {
            // Aplano valores (vienen como "['Nintendo']" o "[]")
            String devName = sanitizeString(r.getDevelopers());
            String platName = sanitizeString(r.getPlatforms());
            String genName = sanitizeString(r.getGenres());

            // Reglas: deben existir los 3
            if (isBlank(devName) || isBlank(platName) || isBlank(genName)) {
                continue;
            }
            validas++;

            // Registrar en maps si no existen
            devs.computeIfAbsent(devName, n -> Desarrollador.builder().nombre(n).build());
            gens.computeIfAbsent(genName, n -> Genero.builder().nombre(n).build());
            plats.computeIfAbsent(platName, n -> Plataforma.builder().nombre(n).build());

            // Normalización de ESRB
            ClasificacionEsrb esrb = null;
            if (!isBlank(r.getEsrbCode())) {
                try {
                    esrb = ClasificacionEsrb.fromCodigo(r.getEsrbCode().trim());
                } catch (IllegalArgumentException ignored) {
                    /* dejar null */ }
            }

            // Armar “pendiente”
            juegosPendientes.add(PendingGame.builder()
                    .titulo(nvl(r.getTitle(), "(sin título)"))
                    .fechaLanzamiento(r.getReleaseDateEpoch())
                    .desarrollador(devName)
                    .genero(genName)
                    .plataforma(platName)
                    .rating(r.getRating())
                    .juegosFinalizados(r.getPlays())
                    .jugando(r.getPlaying())
                    .resumen(nvl(normalizeSummary(r.getSummary()), ""))
                    .esrb(esrb)
                    .build());
        }

        // 2) Persistencia en una sola transacción
        int insDevs, insGens, insPlats, insJuegos;
        EntityManager em = LocalEntityManagerProvider.em();
        try {
            em.getTransaction().begin();

            // Persistir maestras primero (los objetos en Maps aún no tienen ID)
            insDevs = persistMapValues(em, devs);
            insGens = persistMapValues(em, gens);
            insPlats = persistMapValues(em, plats);

            // Crear y persistir juegos referenciando las maestras ya persistidas
            insJuegos = 0;
            for (var pg : juegosPendientes) {
                var juego = Juego.builder()
                        .titulo(pg.getTitulo())
                        .fechaLanzamiento(pg.getFechaLanzamiento())
                        .desarrollador(devs.get(pg.getDesarrollador()))
                        .genero(gens.get(pg.getGenero()))
                        .plataforma(plats.get(pg.getPlataforma()))
                        .rating(pg.getRating())
                        .juegosFinalizados(pg.getJuegosFinalizados())
                        .jugando(pg.getJugando())
                        .resumen(pg.getResumen())
                        .build();
                juego.setClasificacionEsrb(pg.getEsrb());
                em.persist(juego);
                insJuegos++;
            }

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }

        return ImportResult.builder()
                .filasLeidas(rows.size())
                .filasValidas(validas)
                .desarrolladoresInsertados(insDevs)
                .generosInsertados(insGens)
                .plataformasInsertadas(insPlats)
                .juegosInsertados(insJuegos)
                .build();
    }

    // -------- Helpers internos (memoria/persistencia/parseo) --------
    private static <T> int persistMapValues(EntityManager em, Map<String, T> map) {
        int count = 0;
        for (var e : map.entrySet()) {
            em.persist(e.getValue());
            count++;
        }
        // flush no estrictamente necesario, pero útil si luego dependés de IDs ya generados
        em.flush();
        return count;
    }

    /**
     * Convierte "['Nintendo']" -> "Nintendo", "[]" o "" -> null
     */
    private static String sanitizeString(String raw) {
        if (raw == null) {
            return null;
        }
        var t = raw.trim();
        if (t.isEmpty() || "[]".equals(t)) {
            return null;
        }
        // Quita [ '   y   ' ]
        t = t.replaceAll("^\\[\\s*'", "").replaceAll("'\\s*]$", "");
        t = t.trim();
        return t.isEmpty() ? null : t;
    }

    private static String normalizeSummary(String s) {
        if (s == null) {
            return null;
        }
        return s.replace("/n", "\n").trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private static String nvl(String s, String d) {
        return isBlank(s) ? d : s;
    }

    // -------- Modelo temporal para juegos pendientes (en memoria) --------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class PendingGame {

        private String titulo;
        private Integer fechaLanzamiento;
        private String desarrollador;
        private String genero;
        private String plataforma;
        private Double rating;
        private Integer juegosFinalizados;
        private Integer jugando;
        private String resumen;
        private ClasificacionEsrb esrb;
    }
}
