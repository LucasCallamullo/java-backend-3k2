package utils;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private String title;
    private List<MenuOption> options;

    public Menu(String title, List<MenuOption> options) {
        this.title = title;
        this.options = options;
    }

    public void run(AppContext context) {
        
        while (true) {
            int choice = this.showMenu(context);

            if (choice == 0) {
                System.out.println("Saliendo...");
                break;
            }
            
            MenuOption selected = this.options.stream()
                    .filter(opt -> opt.getCode() == choice)
                    .findFirst()
                    .orElse(null);

            if (selected != null) {
                selected.getAction().run(context);
            } else {
                System.out.println("Elija una opción correctamente del menú...");
            }
        }
    }

    private int showMenu(AppContext context) {
        Scanner sc = context.get("sc", Scanner.class);

        System.out.println("\n=== " + this.title + " ===");
        this.options.forEach(opt -> {
            System.out.println(opt);
        });
        System.out.println("0 - Salir.");

        System.out.print("Seleccione opción: ");
        int choice = sc.nextInt();
        return choice;
    }
}
