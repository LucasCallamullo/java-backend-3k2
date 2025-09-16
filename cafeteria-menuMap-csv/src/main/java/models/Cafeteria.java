package models;



import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URISyntaxException;


import cafeteria.App;


public class Cafeteria {
    private Map<String, Category> categories = new HashMap<>();
    private List<Sale> sales = new ArrayList<>();
    
    public void showSales() {
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    public void showSalesSortedByName() {
        List<Sale> sorted = this.sales.stream()
            .sorted(Comparator.comparing(sale -> sale.getName()))        // devuelve un Stream
            .toList();            // casteamos a List para poder recorrerlo.

        // variables para puntos extra, preguntar si deberia ser metodo por separado

        // se crean asi estas variables porque stream(forEach) no permite modificar variables externas..
        int[] totalQuantity = {0};
        double[] totalRevenue = {0.0};

        sorted.forEach(sale -> {
            System.out.println(sale);
            totalQuantity[0] += sale.getQuantitySold();
            totalRevenue[0] += sale.calcularPrecioVenta();
        });

        System.out.printf("Cantidad total vendida: %d%n", totalQuantity[0]);
        System.out.printf("Total recaudado: %.2f%n", totalRevenue[0]);

        /* preguntar si quería que hicieramos todo con stream, o aprovechar el ciclo de muestra
        * para calcular los totales en vez de recorrer cada vez de nuevo las listas.
         
        int totalQuantity = sorted.stream()
            .mapToInt(Product::getQuantitySold)
            .sum();

        double totalRevenue = sorted.stream()
            .mapToDouble(Product::calcularPrecioVenta)
            .sum();

        System.out.printf("Cantidad total vendida: %d%n", totalQuantity);
        System.out.printf("Total recaudado: %.2f%n", totalRevenue);
        */
    }

    public double calculateAverage() {

        // Forma con stream: mapToDouble genera un nuevo DoubleStream, que contiene únicamente 
        // los valores double que devuelve la función lambda para cada elemento del stream original. 
        // Es decir, si tu lambda es sale -> sale.calcularPrecioVenta(), el DoubleStream contendrá
        // {precio1, precio2, precio3, ...} correspondientes a cada venta.
        return this.sales.stream()
            .mapToDouble(sale -> sale.calcularPrecioVenta())
            .average()
            .orElse(0.0);

        /* equivalente sin stream --> 
        double acum = 0.0;
        this.sales.forEach(sale -> {
            acum += sale.calcularPrecioVenta();
        });

        double avg = (this.sales.length > 0) ? 
            acum / this.sales.length : 
            0.0;

        return avg;
        */
    }

    public void showSalesGreaterThanAverage(double avg) {
        long quantity = this.sales.stream()
            .filter(s -> s.calcularPrecioVenta() > avg)    // filtra los que cumplen la condición
            .peek(sale -> System.out.println(sale))  // peek solo recorre los elementos y sigue en Stream
                // a diferencia del forEach que recorre y devuelve void terminando el Stream.
            .count();   // length en stream, devuelve un long y termina stream.

        System.out.println("Cantidad de Ventas que superan el promedio: " + quantity);

        /*  Equivalencia
        this.sales.forEach(sale -> {
            if (sale.calcularPrecioVenta() > avg) {
                System.out.println(sale);
            }
        }); */
    }

    public void showOneCategoryNSales(String categoryName) {
        List<Sale> filtered = this.sales.stream()
            .filter(sale -> sale.getCategory().getName().equals(categoryName))
            .toList(); // convierte el stream a una lista y retorna

        // realmente se podría simplificar en el primer stream con listas
        // ver ejemplo en showSalesSortedByName()

        // reversed es de mayor a menor
        int quantity = filtered.stream()
            .sorted(Comparator.comparing((Sale sale) -> sale.calcularPrecioVenta()).reversed())    
            .peek(sale -> System.out.println(sale))
            .mapToInt(sale -> sale.getQuantitySold())
            .sum();

            // .sorted(Comparator.comparing(sale -> sale.getName())) 
            // .sorted(Comparator.comparing((Sale sale) -> sale.calcularPrecioVenta()).reversed())   // reversed es de mayor a menor

        double totalSold = filtered.stream()
            .mapToDouble(sale -> sale.calcularPrecioVenta())
            .sum();

        System.out.printf(
            "Cantidad de productos vendidos del tipo %s es de %d%n", categoryName, quantity
        );

        System.out.printf(
            "Total recaudado del tipo %s: %.2f%n", categoryName, totalSold
        );
    }

    public Category getCategory(String categoryName) {
        return this.categories.get(categoryName);
    }

    public boolean containsCategory(String categoryName) {
        return this.categories.containsKey(categoryName);
    }

    
    public void showOrderByCodeAndPartitionByDiscount() {
        Map<Boolean, List<Sale>> partitioned = this.sales.stream()
            .collect(Collectors.partitioningBy(sale -> sale.getDiscount() > 1.0));

        partitioned.get(true).stream()
            .sorted(Comparator.comparing(Sale::getCode))    // son otra forma de sintaxis pero
            .forEach(System.out::println);                // es igual a sale -> sale.getCode();
    }

    public void importarDesdeCSV() {
        String folderPath = "/data";   // carpeta dentro de resources
        // int totalLineas = 0;

        try {
            // 1. obtener carpeta "data" desde resources
            URL folderURL = App.class.getResource(folderPath);
            Path dataFolder = Paths.get(folderURL.toURI());

            // 2. listar archivos .csv dentro de la carpeta
            try (Stream<Path> files = Files.list(dataFolder)) {
                files.filter(path -> path.toString().endsWith(".csv"))
                    .forEach(path -> {
                        // para cada CSV encontrado, lo procesamos
                        procesarCSV(path.toFile());
                    });
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // método auxiliar que procesa UN archivo CSV
    private void procesarCSV(File csv) {
        try (Scanner scanner = new Scanner(csv)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;
                if (lineNumber == 1) continue; // omitir encabezado

                // Codigo,NombreProducto,TipoProducto,CantidadVendida,PrecioVenta,Descuento
                String[] items = line.split(",");

                // stupid check
                if (items.length < 6) {
                    System.err.println("Línea inválida (" + lineNumber + "): " + line);
                    continue;
                }

                String code = items[0];
                String name = items[1];
                String categoryName = items[2];

                // Si no lo contiene agrega al hashmap
                Category category;
                if (!this.categories.containsKey(categoryName)) {
                    category = new Category(categoryName);
                    this.categories.put(categoryName, category);
                } else {
                    category = this.categories.get(categoryName);
                }

                int quantitySold = Integer.parseInt(items[3]);
                double price = Double.parseDouble(items[4]);
                double discount = Double.parseDouble(items[5]);

                Sale sale = new Sale(code, name, category, quantitySold, price, discount);
                this.sales.add(sale);
            }
            System.out.printf("Archivo %s procesado.%n", csv.getName());
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        }
    }

    /* ESTE era mi metodo comun con un solo archivo.csv
    public void importarDesdeCSV() {
        String pathFile = "/data/cafelahumedad.csv";  // con "/" para buscar en resources
        Scanner scanner = null;
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
                if (lineNumber == 1) continue;     // omite header

                String[] items = line.split(",");

                // stupid check
                if (items.length < 6) {
                    System.err.println("Línea inválida (" + lineNumber + "): " + line);
                    continue;
                }

                // Codigo,NombreProducto,TipoProducto,CantidadVendida,PrecioVenta,Descuento
                String code = items[0];
                String name = items[1];
                String categoryName = items[2];        // TipoProducto --> category

                // Si no lo contiene agrega al hashmap
                Category category; 
                if (!this.categories.containsKey(categoryName) ) {
                    category = new Category(categoryName);
                    this.categories.put(categoryName, category);
                } else {
                    // si ya la contiene recupera 
                    category = this.categories.get(categoryName);
                }

                int quantitySold = Integer.parseInt(items[3]);    // String --> int
                double price = Double.parseDouble(items[4]);
                double discount = Double.parseDouble(items[5]);

                Sale sale = new Sale(code, name, category, quantitySold, price, discount);

                this.sales.add(sale);
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

        if (lineNumber > 1){
            System.out.printf("Se cargaron %d ventas.%n", lineNumber - 1);
        }
    }
    */

}

