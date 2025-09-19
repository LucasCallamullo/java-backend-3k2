package museo.arte.menu;


public class ItemMenu<T> {
    private final String text;
    private final OptionMenu<T> action;

    public ItemMenu(String text, OptionMenu<T> accion) {
        this.text = text;
        this.action = accion;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public void ejecutar(T context) {
        if (this.action != null) {
            this.action.invocar(context); // delega la acción
        }
    }
}

/*Legado esto estaba antes como forma simple con metodo en menu en vez de clase que delega la accion
public abstract class ItemMenu<T> {
    private String text;

    public ItemMenu(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    // este metodo se llama desde el get option del menu
    public abstract void ejecutar(T context);
}
 */
