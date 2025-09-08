package utils;

@FunctionalInterface
public interface FuncAction {

    // el metodo recibe el contexto para utilizar es unico
    void run(AppContext context);
}

