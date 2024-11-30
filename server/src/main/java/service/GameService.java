package service;

import chess.ChessGame;
import chess.ChessMove;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.*;

import java.util.Collection;

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

    public Collection<GameData> listGames(AuthData auth) throws DataAccessException {
        authService.getAuth(auth.authToken());
        return gameDAO.listGames();
    }

    public int createGame(GameData gameData, AuthData auth) throws DataAccessException {
        authService.getAuth(auth.authToken());
        return this.gameDAO.createGame(gameData);
    }

    public void joinGame(JoinGameRequest joinRequest, AuthData auth) throws DataAccessException {
        authService.getAuth(auth.authToken());
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

    public void makeMove(ChessMove newMove, AuthData auth) throws DataAccessException {
        authService.getAuth(auth.authToken());
    }

    public GameData getGame(Integer gameID, AuthData auth) throws DataAccessException {
        authService.getAuth(auth.authToken());
        return this.gameDAO.getGame(gameID);
    }
}
