package utnfc.isi.back.repositories;

import utnfc.isi.back.entities.BoardGame;

public class BoardGameRepository extends Repository<BoardGame, Integer> {

    public BoardGameRepository() {
        super();
    }

    @Override
    protected Class<BoardGame> getEntityClass(){
        return BoardGame.class;
    }
}