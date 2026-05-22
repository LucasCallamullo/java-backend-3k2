package utnfc.isi.back.sim;

import utnfc.isi.back.sim.csv.CsvLoader;
import utnfc.isi.back.sim.repository.JpaDesarrolladorRepository;
import utnfc.isi.back.sim.repository.JpaGeneroRepository;
import utnfc.isi.back.sim.service.ImportService;
import utnfc.isi.back.sim.repository.JpaJuegoRepository;

public class AppMain {
  public static void main(String[] args) throws Exception {
    /* if (args.length==0) {
      System.out.println("Uso: mvn -q exec:java -Dexec.args=\"/path/games_data.csv\"");
      return;
    } */
    var path = args[0];

    // 1) Cargar CSV a memoria (DTOs)
    // 2) Popular BD (con reglas de filtrado + parseo)
    // 3) Top-5 géneros por 'jugando'
    // 4) Desarrolladores con > 30 juegos
    // 5) Plataforma mejor rankeada (promedio rating) filtrando finalizados > 999

    // 1) Cargar CSV a memoria (DTOs)
    var rows = CsvLoader.read(path);
    System.out.println("Filas leídas CSV: " + rows.size());

    // 2) Popular BD (con reglas de filtrado + parseo)
    var svc = new ImportService();
    var cargados = svc.importar(rows);
    System.out.println("Resultado de la carga: " + cargados);

    // 3) Top-5 géneros por 'jugando'
    var juegoRepo = new JpaJuegoRepository();
    var generoRepo = new JpaGeneroRepository();

    var top = juegoRepo.top5GenerosPorJugando();
    System.out.println("\nTop 5 Géneros por 'jugando':");
    top.forEach(r -> System.out.printf("  %s => %s\n", r[0], r[1]));

    System.out.println("Cantidad total de Géneros: " + generoRepo.count());

    // 4) Desarrolladores con > 30 juegos
    var desaRepo = new JpaDesarrolladorRepository();
    var devs = juegoRepo.desarrolladoresConMasDeN(30);
    System.out.println("\nDesarrolladores con > 30 juegos:");
    devs.forEach(r -> System.out.printf("  %s => %s\n", r[0], r[1]));

    System.out.println("Cantidad total de Desarrolladores: " + desaRepo.count());

    // 5) Plataforma mejor rankeada (promedio rating) filtrando finalizados > 999
    var rankPlat = juegoRepo.rankingPlataformasPorRatingPromedio(999);
    if (!rankPlat.isEmpty()) {
      var best = rankPlat.get(0);
      System.out.printf("\nPlataforma mejor rankeada (rating prom., finalizados > 999): %s => %.3f\n",
          best[0], best[1]);
    } else {
      System.out.println("\nNo hay plataformas con juegos_finalizados > 999 y rating válido.");
    }
  }
}
