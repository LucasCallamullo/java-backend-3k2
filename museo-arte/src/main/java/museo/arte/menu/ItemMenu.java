package museo.arte.menu;


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
