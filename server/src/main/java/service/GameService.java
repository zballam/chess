package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class GameService {
    GameDAO gameDAO;
    AuthService authService;
    UserService userService;

    public GameService(GameDAO gameDAO, AuthService authService, UserService userService) {
        this.gameDAO = gameDAO;
        this.authService = authService;
        this.userService = userService;
    }

    public void clear() {
        try {
            this.gameDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    public GameData getGame(int gameID) throws DataAccessException {
//        return this.gameDAO.getGame(gameID);
//    }

    public Collection<GameData> listGames(AuthData auth) throws DataAccessException {
        authService.authenticate(auth);
        return gameDAO.listGames();
    }

    public int createGame(GameData gameData, AuthData auth) throws DataAccessException {
        authService.authenticate(auth);
        return this.gameDAO.createGame(gameData);
    }

    public void joinGame(JoinGameRequest joinRequest, AuthData auth) throws DataAccessException {
        authService.authenticate(auth);
        // Check to make sure user color isn't already taken
        GameData game = gameDAO.getGame(joinRequest.gameID());
        if (game == null) { throw new DataAccessException("Bad request"); }
        if (joinRequest.playerColor() == ChessGame.TeamColor.WHITE && game.whiteUsername() != null) {
            throw new DataAccessException("Already taken");
        }
        else if (joinRequest.playerColor() == ChessGame.TeamColor.BLACK && game.blackUsername() != null) {
            throw new DataAccessException("Already taken");
        }
        String username = this.authService.getAuth(auth.authToken()).username();
        UserData user = userService.userDAO.getUser(username);
        gameDAO.insertUser(joinRequest.gameID(), user, joinRequest.playerColor());
    }
}
