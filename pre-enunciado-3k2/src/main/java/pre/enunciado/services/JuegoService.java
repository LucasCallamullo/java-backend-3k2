package pre.enunciado.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import pre.enunciado.entities.Juego;
import pre.enunciado.repositories.JuegoRepository;

import pre.enunciado.services.interfaces.AbstractService;

public class JuegoService extends AbstractService<Juego, Integer> {

    private final DesarrolladorService desarrolladorService;


    public JuegoService() {
        super(new JuegoRepository());
        this.desarrolladorService = new DesarrolladorService();

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
                    Juego jogo = procesarLinea(linea);
                    this.repository.create(jogo);
                });
    }

    private Juego procesarLinea(String linea) {
        // Title;Release_Date;Developers;Summary;Platforms;Genres;Rating;Plays;Playing;esrb_rating
        //    0        1            2       3        4        5      6     7        8        9
        String[] tokens = linea.split(";");

        Juego jogo = new Juego();

        jogo.setTitulo(tokens[0]);

        // para guardar solo el año, esta gaga lo de los milisegundos
        if (tokens[1] != null && 
            !tokens[1].trim().isEmpty() && 
            !tokens[1].equalsIgnoreCase("TBD")) {

            String[] date = tokens[1].split(",");

            if (date.length == 2) {
                jogo.setFechaLanzamiento(Integer.parseInt(date[1].trim()));
            }
        }

        String devName = tokens[2]
            .replace("[", "")
            .replace("]", "")
            .replace("'", "")
            .trim();

        // Verificar que el nombre no esté vacío antes de crear o buscar
        if (!devName.isEmpty()) {
            var dev = this.desarrolladorService.getOrCreateByName(devName);
            jogo.setDesarrollador(dev);
        } else {
            // Si está vacío, podés dejarlo nulo
            jogo.setDesarrollador(null);
        }


        jogo.setResumen(tokens[3]);

        return jogo;

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