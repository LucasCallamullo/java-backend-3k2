package utils;

import java.util.Scanner;

public interface IMenu<T> {
    void registrarOpcion(int opcion, MenuOption<T> action);
    void invocarAccion(T context);
}


