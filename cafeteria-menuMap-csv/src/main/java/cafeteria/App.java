package cafeteria;

import java.util.Scanner;
// import java.util.ArrayList;
import utils.AppContext;
import utils.MenuOption;
import utils.Menu;

import models.Cafeteria;

public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        var context = AppContext.getInstance();
        String coffeName = "coffeeShop";

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();

        menu.registrarOpcion(1, new MenuOption<>(1, "Cargar productos desde CSV.") {
            @Override
            public void ejecutar(AppContext context) {
                if (!context.contains(coffeName)) {
                    Cafeteria coffeeShop = new Cafeteria();
                    context.put(coffeName, coffeeShop);
                    coffeeShop.importarDesdeCSV();
                } else {
                    System.out.println("Ya cargaste los datos!");
                }
            }
        });

        menu.registrarOpcion(2, new MenuOption<>(2, "Mostrar Ventas ordenadas por Nombre y Totales.") {
            @Override
            public void ejecutar(AppContext context) {
                if (!context.contains(coffeName)) {
                    System.out.println("Deja de boludear pasar por la opcion 1..");
                    return;
                }
                // Recuperar la instancia de Cafeteria desde el contexto
                Cafeteria coffeeShop = context.get(coffeName, Cafeteria.class);
                coffeeShop.showSalesSortedByName();
            }
        });

        menu.registrarOpcion(3, new MenuOption<>(3, "Cuantos precios de ventas superan el promedio precio de ventas.") {
            @Override
            public void ejecutar(AppContext context) {
                if (!context.contains(coffeName)) {
                    System.out.println("Deja de boludear pasar por la opcion 1..");
                    return;
                }
                // Recuperar la instancia de Cafeteria desde el contexto
                Cafeteria coffeeShop = context.get(coffeName, Cafeteria.class);
                double avg = coffeeShop.calculateAverage();
                coffeeShop.showSalesGreaterThanAverage(avg);
            }
        });

        menu.registrarOpcion(4, new MenuOption<>(4, "punto 4 redactado con los codos, preguntar.") {
            @Override
            public void ejecutar(AppContext context) {
            }
        });

        menu.registrarOpcion(5, new MenuOption<>(5, "Ingresar tipoProducto y totalizar.") {
            @Override
            public void ejecutar(AppContext context) {
        
                if (!context.contains(coffeName)) {
                    System.out.println("Deja de boludear pasar por la opcion 1..");
                    return;
                }

                // Recuperar la instancia de Cafeteria desde el contexto
                Cafeteria coffeeShop = context.get(coffeName, Cafeteria.class);
                
                // pedir String por fuera del metodo ?
                String categoryName;
                while (true) {
                    System.out.print("Ingrese el valor de Categoría a buscar: ");
                    Scanner sc = context.get("scanner", Scanner.class);
                    categoryName = sc.nextLine();
                    if (coffeeShop.containsCategory(categoryName)) break;
                }
                coffeeShop.showOneCategoryNSales(categoryName);
            }
        });

        menu.registrarOpcion(6, new MenuOption<>(6, "Mostrar ordenado por codigo y descuento mayor al 1%.") {
            @Override
            public void ejecutar(AppContext context) {
        
                if (!context.contains(coffeName)) {
                    System.out.println("Deja de boludear pasar por la opcion 1..");
                    return;
                }

                // Recuperar la instancia de Cafeteria desde el contexto
                Cafeteria coffeeShop = context.get(coffeName, Cafeteria.class);
                coffeeShop.showOrderByCodeAndPartitionByDiscount();
            }
        });

        // inicializamos un unico serializer en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);    // preguntar sobre opciones del scanner

        menu.invocarAccion(context);
    }
}
