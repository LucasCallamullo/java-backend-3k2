package utnfc.isi.back;

import java.net.URL;
import java.util.Scanner;

import utnfc.isi.back.app.Actions;
import utnfc.isi.back.app.AppContext;
import utnfc.isi.back.infra.DatabaseInitializer;
import utnfc.isi.back.menu.ItemMenu;
import utnfc.isi.back.menu.Menu;

import utnfc.isi.back.services.BoardGameService;

/* 
public class App {
    public static void main(String[] args) {
        DatabaseInitializer.recreateSchema();

        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();
        URL folderPath = App.class.getResource("/files");
        context.put("path", folderPath);
        context.registerService(BoardGameService.class, new BoardGameService());

        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc); 

        Actions actions = new Actions();
        // Opcion 1 - Importar desde CSV
        actions.importarDesdeCsv(context);

        // Opcion 2 - Listar
        actions.listarJuegos(context);
    }
}
*/


public class App {
    public static void main(String[] args) {
        DatabaseInitializer.recreateSchema();

        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();
        
        URL folderPath = App.class.getResource("/files");
        context.put("path", folderPath);
        context.registerService(BoardGameService.class, new BoardGameService());

        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);

        Actions actions = new Actions();


        menu.addOption(1, new ItemMenu<>(
            "Cargar Juegos de mesa desde CSV", 
            actions::importarDesdeCsv
        ));

        menu.addOption(2, new ItemMenu<>(
            "Listar Juegos de mesa desde DB", 
            actions::listarJuegos
        ));

        /*
         * AGREGADOS MIOS BORRAR DESPUES
         * 
         */
        menu.addOption(3, new ItemMenu<>(
            "Top-5 categorías con más jugadores activos", 
            actions::topFivePlayersForCategory
        ));

        menu.addOption(4, new ItemMenu<>(
            "Diseñadores con más de 30 juegos", 
            actions::getDesignersThirdteenMore
        ));

        menu.addOption(5, new ItemMenu<>(
            "Mejor juego según rating promedio con filtros", 
            actions::bestGameByRating
        ));

        menu.addOption(6, new ItemMenu<>(
            "Juegos aptos para grupo y edad", 
            actions::listGamesByAgeAndPlayers
        ));

        menu.runMenu(context);
    }
}
