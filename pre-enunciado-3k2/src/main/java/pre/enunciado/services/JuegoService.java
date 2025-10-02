package pre.enunciado.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import pre.enunciado.entities.Juego;
import pre.enunciado.repositories.JuegoRepository;

import pre.enunciado.services.interfaces.AbstractService;

public class JuegoService extends AbstractService<Juego, Integer> {

    public JuegoService() {
        super(new JuegoRepository());
    }

    /* este metodo no deberia usarse */
    @Override
    protected Juego createNewEntity(String name) {
        return null;
        // Juego genero = new Juego();
        // genero.setTitulo(name);
        // return genero;
    }


    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1) // saltear cabecera
                .forEach(linea -> {
                    Juego obra = procesarLinea(linea);
                    this.repository.create(obra);
                });
    }

    private Juego procesarLinea(String linea) {
        return null;

        /* 
        String[] tokens = linea.split(",");
        // tokens = ["aca", "algo", "hola"]

        ObraArtistica obra = new ObraArtistica();

        String nombre = tokens[2];
        var autor = autorService.getOrCreateByName(nombre);
        obra.setAutor(autor);

        nombre = tokens[3];
        var museo = museoService.getOrCreateByName(nombre);
        obra.setMuseo(museo);

        nombre = tokens[4];
        var estilo = estiloArtisticoService.getOrCreateByName(nombre);
        obra.setEstilo(estilo);

        obra.setNombre(tokens[0]);
        obra.setAnio(tokens[1]);
        obra.setMontoAsegurado(Double.parseDouble(tokens[5]));
        obra.setSeguroTotal(tokens[6].equalsIgnoreCase("1"));
        return obra;
        */
        
    }
}