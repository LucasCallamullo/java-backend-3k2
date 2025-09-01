package com.ejemplo.alquileres;

import utils.MyArray;

import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class App {
    // scanner global para usar la consola en cada while
    static Scanner sc = new Scanner(System.in);

    public static int menu() {
        System.out.println();
        System.out.println("1 - Cargar arreglo.");
        System.out.println("2 - Mostrar arreglo.");
        System.out.println("3 - Buscar por provincia.");
        System.out.println("4 - Totalizar alquileres.");
        System.out.println("0 - Salir.");
        System.out.print("Ingrese su opción: ");
        int op = sc.nextInt();
        sc.nextLine(); // consume el salto de línea que queda en el buffer
        return op;
    }

    public static void main(String[] args) {
        // inicializar con vlaor por defecto para evitar errores en foreach posteriores
        MyArray<Alquiler> listaAlquileres = new MyArray<>(10);

        int op = 1;
        while (op != 0) {

            op = menu();
            
            switch (op) {
                case 1:
                    // ruta dinamica como parametro
                    String pathFile = "/alquileres_verano.csv";
                    listaAlquileres = cargarAlquileres(pathFile);
                    break;

                case 2:
                    for (Alquiler alquiler : listaAlquileres) {
                        System.out.println(alquiler);
                    }
                    break;

                case 3:
                    System.out.print("Ingrese provincia a buscar: ");
                    String provincia = sc.nextLine();
                    busquedaSecuencial(listaAlquileres, provincia);
                    break;

                case 4:
                    totalAlquileres(listaAlquileres);

                default:
                    break;
            }
        }
    }

    public static void totalAlquileres(MyArray<Alquiler> lista){
        double acum = 0;
        for (Alquiler alquiler : lista){
            acum += alquiler.calcularAlquiler();
        }
        // limita a dos decimales el print
        System.out.printf("El acumulado total es: %.2f%n", acum);
    }
    
    public static int busquedaSecuencial(MyArray<Alquiler> list, String province) {
        int count = 0;

        for (Alquiler alquiler : list) {
            Localizacion localizacion = alquiler.getDireccionPropiedad();
            String city = localizacion.getCiudad();

            // Comparar Strings correctamente
            if (city.equals(province)) count++;
        }

        System.out.printf("Hay %d alquileres con el nombre de la provincia: %s%n", count, province);
        return count; // retornamos la cantidad de aciertos para tests
    }
    

    public static MyArray<Alquiler> cargarAlquileres(String pathFile) {
        Scanner scanner = null;
        MyArray<Alquiler> tempList = new MyArray<>(50);
         int lineNumber = 0;        // contar cantidad de lineas- evitar primera

        try {
            // src/main/resources/archivo.csv        -> getResource("/archivo.csv")
            // src/main/resources/data/archivo.csv   -> getResource("/data/archivo.csv")
            URL location = App.class.getResource(pathFile);
            File csv = new File(location.toURI());
            scanner = new Scanner(csv);

            // saltar la primera línea
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;
                if (lineNumber == 1) continue; 

                String[] items = line.split(";");

                /* Generar cliente dinamicamente */
                String dni = items[1];    // ver csv
                String nombre = items[2];
                String telefono = items[3];
                String email = items[4];
                Cliente cliente = new Cliente(dni, nombre, telefono, email);

                /* Generar localizacion dinamicamente */
                String dire = items[5];
                String ciudad = items[6];
                String codigoPostal = items[7];
                Localizacion direccion = new Localizacion(dire, ciudad, codigoPostal);

                /*
                 * Para convertir Strings a tipos primitivos se usan los métodos estáticos 
                 * de las clases envolventes (Integer, Double, etc.):
                 */
                String codigo = items[0];    // ver csv
                int huespedes = Integer.parseInt(items[8]);       // String -> int
                int diasAlquiler = Integer.parseInt(items[9]);   // String -> int
                double costoPorDia = Double.parseDouble(items[10]); // String -> double

                Alquiler alquiler = new Alquiler(
                    codigo, huespedes, diasAlquiler, costoPorDia, cliente, direccion
                );

                tempList.append(alquiler);
            }

        // captura de errores
        } catch (URISyntaxException e) {
            System.err.println("Error en la URL: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        if (lineNumber > 0){
            System.out.printf("Se cargaron %d alquileres.", lineNumber);
        }

        return tempList;
    }
}
