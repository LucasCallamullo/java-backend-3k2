package museo.arte.app;

import java.util.Scanner;
// import java.util.ArrayList;
import museo.arte.menu.Menu;
import museo.arte.menu.ItemMenu;

import museo.arte.services.ObraArtisticaService;
import museo.arte.services.EstiloArtisticoService;


public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        var context = AppContext.getInstance();

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();
        
        // menu.setTitulo("Menu de Opciones para Museo"); // capaz agregar atributo
        URL folderPath = App.class.getResource("/files");
        context.put("path", folderPath);
        context.registerService(ObraArtisticaService.class, new ObraArtisticaService());
        context.registerService(EstiloArtisticoService.class, new EstiloArtisticoService());

        /* 
         * Opcion 1: 
         * Cargar todos los datos de todas las obras en la base de datos, asociando cada obra a los 
         * objetos necesarios y teniendo en cuenta que cada uno de los objetos asociados debe existir 
         * una y solo una vez en la memoria.
         */
        menu.addOption(1, new ItemMenu<>("Cargar prodductos desde CSV.") {
            @Override
            public void ejecutar(AppContext context) {
                System.out.println("Ya cargaste los datos!");
            }
        });

        /*
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
        */

        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);    // preguntar sobre opciones del scanner

        menu.runMenu(context);
    }
}



/*
public class Main {
    public static void main(String[] args) {

        // Creamos el repositorio
        AutorRepository autorRepo = new AutorRepository();

        // 1️⃣ Crear nuevos autores
        Autor a1 = new Autor();
        a1.setNombre("Picasso");

        Autor a2 = new Autor();
        a2.setNombre("Dalí");

        // Guardarlos en la base
        autorRepo.add(a1);
        autorRepo.add(a2);

        // 2️⃣ Recuperar un autor por ID
        Autor picasso = autorRepo.getById(a1.getId());
        System.out.println("Autor recuperado por ID: " + picasso.getNombre());

        // 3️⃣ Recuperar todos los autores
        Set<Autor> todos = autorRepo.getAll();
        todos.forEach(a -> System.out.println(a.getNombre()));

        // 4️⃣ Recuperar todos como Stream
        Stream<Autor> stream = autorRepo.getAllStrem();
        stream.forEach(a -> System.out.println("Stream autor: " + a.getNombre()));
    }
}
 */