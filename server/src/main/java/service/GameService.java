package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.*;

import java.util.Collection;

public class GameService {
    GameDAO gameDAO;
    AuthService authService;

    public GameService(GameDAO gameDAO, AuthService authService) {
        this.gameDAO = gameDAO;
        this.authService = authService;
    }

    public void clear() {
        try {
            this.gameDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return this.gameDAO.getGame(gameID);
    }

    public Collection<ChessGame> listGames() {
        throw new RuntimeException("Not implemented");
    }

    public int createGame(GameData gameData, AuthData auth) throws DataAccessException {
        String authString = authService.getAuth(auth.authToken());
        if (authString == null) { throw new DataAccessException("AuthToken doesn't exist"); }
        return this.gameDAO.createGame(gameData);
    }

    public void addPlayer(UserData user) {
        throw new RuntimeException("Not implemented");
    }
}
