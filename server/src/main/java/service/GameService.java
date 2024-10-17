package service;

import chess.ChessGame;
import dataaccess.GameDAO;
import model.*;

import java.util.Collection;

public class GameService {
    GameDAO gameDAO;

    public GameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void clear() {
        throw new RuntimeException("Not implemented");
    }

    public GameData getGame() {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessGame> listGames() {
        throw new RuntimeException("Not implemented");
    }

//    public GameData createGame() {
//        throw new RuntimeException("Not implemented");
//    }

    public void addPlayer(UserData user) {
        throw new RuntimeException("Not implemented");
    }
}
