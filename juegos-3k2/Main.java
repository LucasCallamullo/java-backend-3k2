package ar.edu.utn.frc;

import ar.edu.utn.frc.Repository.DbInit;
import ar.edu.utn.frc.service.Menu;

public class Main {
    public static void main(String[] args)throws Exception {
        DbInit.run();
        Menu menu = new Menu("Menú de Opciones de Películas");


    }
}