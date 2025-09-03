package listas.menu;

// import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.AppContext;
import utils.MenuOption;
import utils.Menu;


public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        var context = AppContext.getInstance();
        context.put("instrucciones", "Demo etapa 2");

        // inicializamos un unico scanner y guardamos en context
        Scanner sc = new Scanner(System.in);
        context.put("sc", sc);
        
        // generar lista de opciones INMUTABLE
        List<MenuOption> options = List.of(
            new MenuOption(1, "Hola mundo", c -> System.out.println("¡Hola!")),
            new MenuOption(2, "Mostrar hora", c -> System.out.println(java.time.LocalTime.now()))
        );

        // generar lista de opciones MUTABLE
        /* List<MenuOption> options = new ArrayList<>(10);
        options.add(
            new MenuOption(1, "Hola mundo", c -> System.out.println("¡Hola!"))
        );
        options.add(
            new MenuOption(2, "Mostrar hora", c -> System.out.println(java.time.LocalTime.now()))
        ); */

        new Menu("Menú Funcional — Etapa 2", options).run(context);
    }
}


/*
List<Integer> numeros = List.of(10, 20, 30, 40, 50);

Integer resultado = null;
for (Integer n : numeros) {
    if (n > 25) {
        resultado = n;
        break;
    }
}
System.out.println(resultado); // 30


List<Integer> numeros = List.of(10, 20, 30, 40, 50);

Integer resultado = numeros.stream()
    .filter(n -> n > 25)   // quedate solo con mayores a 25
    .findFirst()           // tomá el primero
    .orElse(-1);           // si no hay ninguno, devolvé -1

System.out.println(resultado); // 30


public class App {
    public static void main(String[] args) {
        // Declaramos la lista usando la interfaz List (buena práctica)
        List<String> nombres = new ArrayList<>();

        // Agregar elementos
        nombres.add("Juan");
        nombres.add("María");
        nombres.add("Pedro");

        // Insertar en un índice específico
        nombres.add(1, "Ana");

        // Obtener un elemento por índice
        String primero = nombres.get(0);
        System.out.println("Primer nombre: " + primero);

        // Modificar un elemento
        nombres.set(2, "Carlos");

        // Eliminar por índice
        nombres.remove(1); // elimina "Ana"

        // Eliminar por objeto
        nombres.remove("Pedro");

        // Verificar si contiene un elemento
        System.out.println("¿Contiene a María? " + nombres.contains("María"));

        // Tamaño de la lista
        System.out.println("Tamaño de la lista: " + nombres.size());

        // Recorrer con for clásico
        System.out.println("\nRecorrido con for clásico:");
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(i + ": " + nombres.get(i));
        }

        // Recorrer con for-each
        System.out.println("\nRecorrido con for-each:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        // Recorrer con lambda (Java 8+)
        System.out.println("\nRecorrido con lambda:");
        nombres.forEach(n -> System.out.println(n));
    }
}


import java.util.HashMap;
import java.util.Map;

public class EjemploMap {
    public static void main(String[] args) {
        // Crear un diccionario (clave → valor)
        Map<Integer, String> usuarios = new HashMap<>();

        // Agregar elementos
        usuarios.put(1, "Juan");
        usuarios.put(2, "María");
        usuarios.put(3, "Pedro");

        // Acceder por clave
        System.out.println(usuarios.get(2)); // María

        // Modificar valor existente
        usuarios.put(3, "Carlos"); // reemplaza "Pedro" → "Carlos"

        // Eliminar por clave
        usuarios.remove(1); // elimina "Juan"

        // Recorrer todas las entradas
        for (Map.Entry<Integer, String> entry : usuarios.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}
*/