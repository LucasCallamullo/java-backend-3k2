package utils;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class Menu<T> implements IMenu<T> {
    private Map<Integer, MenuOption<T>> actions = new HashMap<>();

    @Override
    public void registrarOpcion(int opcion, MenuOption<T> action) {
        this.actions.put(opcion, action);
        System.out.println("Opción " + opcion + ": " + action + " registrada.");
    }

    @Override
    public void invocarAccion(T context) {
        while (true) {
            int choice = this.showMenu(context);

            if (choice == 0) {
                System.out.println("Saliendo...");
                break;
            }

            if (actions.containsKey(choice)) {
                actions.get(choice).ejecutar(context);
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    private int showMenu(T context) {
        if (context instanceof AppContext appCtx) {
            Scanner sc = appCtx.get("scanner", Scanner.class);

            System.out.println("Opciones disponibles:");
            for (MenuOption<T> option : actions.values()) {
                System.out.println(option);
            }
            System.out.println("0 - Salir.");

            System.out.print("Seleccione opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Intente de nuevo:");
                sc.next(); // consumir la entrada no válida
            }

            return sc.nextInt();
        } else {
            throw new IllegalArgumentException("El contexto no es AppContext");
        }
    }
}
