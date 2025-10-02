package pre.enunciado.app;

import java.util.Scanner;
import java.net.URL;

// import java.util.ArrayList;
import pre.enunciado.menu.Menu;
import pre.enunciado.menu.ItemMenu;

import pre.enunciado.services.JuegoService;
import pre.enunciado.services.GeneroService;


public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();
        // menu.setTitulo("Menu de Opciones para Museo"); // capaz agregar atributo
        URL folderPath = App.class.getResource("/files");
        context.put("path", folderPath);
        
        context.registerService(JuegoService.class, new JuegoService());
        context.registerService(GeneroService.class, new GeneroService());

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();

        Actions actions = new Actions();

        menu.addOption(1, new ItemMenu<>("Decirme Hola Mundo", actions::saludar));
        menu.addOption(2, new ItemMenu<>("Cargar obras desde CSV", actions::importarObras));
        // menu.addOption(2, new ItemMenu<>("Listar obras artísticas", actions::listarObras));

        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);    // preguntar sobre opciones del scanner

        menu.runMenu(context);
    }
}


