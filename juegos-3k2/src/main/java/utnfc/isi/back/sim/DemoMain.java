package utnfc.isi.back.sim;

import utnfc.isi.back.sim.domain.*;
import utnfc.isi.back.sim.infra.LocalEntityManagerProvider;
import utnfc.isi.back.sim.repository.*;

public class DemoMain {

    public static void main(String[] args) {

        // Repos
        var repoDesa = new JpaDesarrolladorRepository();
        var repoGen = new JpaGeneroRepository();
        var repoPlat = new JpaPlataformaRepository();
        var repoJuego = new JpaJuegoRepository();

        // ----- Referencias compartidas / distintas -----
        // Desarrolladores: Larian (compartido por 2), FromSoftware (distinto)
        var larian = Desarrollador.builder().nombre("Larian Studios").build();
        larian = repoDesa.save(larian);

        var fromSoftware = Desarrollador.builder().nombre("FromSoftware").build();
        fromSoftware = repoDesa.save(fromSoftware);

        // Géneros: RPG (compartido por 2), Shooter (distinto)
        var rpg = Genero.builder().nombre("RPG").build();
        rpg = repoGen.save(rpg);

        var shooter = Genero.builder().nombre("Shooter").build();
        shooter = repoGen.save(shooter);

        // Plataformas: PC (compartida por 2), Nintendo Switch (distinta)
        var pc = Plataforma.builder().nombre("PC").build();
        pc = repoPlat.save(pc);

        var switchPlat = Plataforma.builder().nombre("Nintendo Switch").build();
        switchPlat = repoPlat.save(switchPlat);

        // ----- Juegos (3) -----
        var j1 = Juego.builder()
                .titulo("Baldur's Gate III")
                .fechaLanzamiento(2023)
                .desarrollador(larian) // compartido
                .genero(rpg) // compartido
                .plataforma(pc) // compartida
                // .clasificacionEsrb(ClasificacionEsrb.M)
                .rating(9.6)
                .juegosFinalizados(1000000)
                .jugando(150000)
                .resumen("RPG épico basado en D&D.")
                .build();
        j1.setClasificacionEsrb(ClasificacionEsrb.M);
        j1 = repoJuego.save(j1);

        var j2 = Juego.builder()
                .titulo("Divinity: Original Sin 2")
                .fechaLanzamiento(2017)
                .desarrollador(larian) // compartido
                .genero(rpg) // compartido
                .plataforma(pc) // compartida
                // .clasificacionEsrb(ClasificacionEsrb.T)
                .rating(9.3)
                .juegosFinalizados(800000)
                .jugando(50000)
                .resumen("RPG táctico con party y libertad total.")
                .build();
        j2.setClasificacionEsrb(ClasificacionEsrb.T);
        j2 = repoJuego.save(j2);

        var j3 = Juego.builder()
                .titulo("Metroid Prime Remastered")
                .fechaLanzamiento(2023)
                .desarrollador(fromSoftware) // distinto
                .genero(shooter) // distinto
                .plataforma(switchPlat) // distinta
                // .clasificacionEsrb(ClasificacionEsrb.E10)
                .rating(9.0)
                .juegosFinalizados(300000)
                .jugando(20000)
                .resumen("Aventura shooter en primera persona remasterizada.")
                .build();
        j3.setClasificacionEsrb(ClasificacionEsrb.E10);
        j3 = repoJuego.save(j3);

        // ----- Listado final -----
        System.out.println("=== Juegos cargados ===");

        for (var j : repoJuego.findAllWithRefs()) {
            String dev = j.getDesarrollador() != null ? j.getDesarrollador().getNombre() : "(sin desarrollador)";
            String plat = j.getPlataforma() != null ? j.getPlataforma().getNombre() : "(sin plataforma)";
            String gen = j.getGenero() != null ? j.getGenero().getNombre() : "(sin género)";
            String esrb = j.getClasificacionEsrb() != null ? j.getClasificacionEsrb().getNombre() : "(sin clasificación)";
            System.out.printf("• %s | Dev: %s | Plat: %s | Género: %s | ESRB: %s%n",
                    j.getTitulo(), dev, plat, gen, esrb);
        }
        System.out.println("======================");
        System.out.println("Total juegos: " + repoJuego.count() + " (esperado: 3)");
        System.out.println("Total Generos: " + repoGen.count() + " (esperado: 2)");
        System.out.println("Total Desarrolladores: " + repoDesa.count() + " (esperado: 2)");
        System.out.println("Total Plataformas: " + repoPlat.count() + " (esperado: 2)");
        System.out.println("======================");

        LocalEntityManagerProvider.close();
    }
}
