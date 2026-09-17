package ar.edu.backend;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {

        // InputStream --> encontrar el archivo
        // Path archivo = Path.of(args.length == 0 ? "datos/datos.csv" : args[0]);
        Path archivo = Path.of(args.length == 0 ? "datos/datos_parcial.csv" : args[0]);

        ResultadoProceso resultado = new ParserSolicitudes().procesar(archivo);

        CarteraCreditos cartera = new CarteraCreditos(resultado.getCreditos());

        System.out.println(resultado.informe());
        resultado.getDescartes().forEach(System.out::println);
        resultado.getErrores().forEach(System.out::println);

        System.out.println(cartera.informe());

        System.out.println("=====================================================");
        System.out.println("=====================================================");
        System.out.println("=====================================================");
        System.out.println("=====================================================");
        System.out.println("=====================================================");

        var mapita = cartera.profesMantenseYGrabenloProqueNopuedeserqueseantanvagos();
        mapita.forEach((clave, valor) -> {
            System.out.println("Clave:" + clave + "- Cantidad:" + valor);
        });
    }
}
