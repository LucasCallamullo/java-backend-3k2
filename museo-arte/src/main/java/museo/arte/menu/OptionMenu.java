package museo.arte.menu;

@FunctionalInterface
public interface OptionMenu<T> {
    void invocar(T context);
}