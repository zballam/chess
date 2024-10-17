package dataaccess;

import chess.ChessGame;
import model.UserData;
import service.GameService;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    public void clear() throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }

//    public void createGame(GameService createRequest, String authToken) throws DataAccessException {
//        throw new RuntimeException("Not implemented");
//    }

    public ChessGame getGame(int gameID) throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessGame> listGames() throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }

    public void insertUser(UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }
}
