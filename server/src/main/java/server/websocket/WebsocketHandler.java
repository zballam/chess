package server.websocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import service.AuthService;
import service.GameService;
import websocket.commands.*;
import websocket.messages.*;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final GameService gameService;
    private final AuthService authService;

    public WebsocketHandler(GameService gameService, AuthService authService) {
        this.gameService = gameService;
        this.authService = authService;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        // Define customDeserializer for UserGameCommands
        Gson customDeserializer = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer()).create();
        var userGameCommand = customDeserializer.fromJson(message, UserGameCommand.class);
        // Check to see if is a MakeMoveCommand
        if (userGameCommand instanceof MakeMoveCommand) { // MakeMoveCommand class
            makeMoveCommand((MakeMoveCommand) userGameCommand, session);
        }
        // Otherwise...
        else { // UserGameCommand class
            if (userGameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                try {
                    AuthData userAuthData = authService.getAuth(userGameCommand.getAuthToken());
                    connectCommand(userGameCommand.getGameID(), userAuthData, session);
                } catch (DataAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (userGameCommand.getCommandType() == UserGameCommand.CommandType.LEAVE) {
                leaveCommand(userGameCommand, session);
            }
            // This else-if will trigger when there was no move being made to deserialize the class into a MakeMoveCommand
            else if (userGameCommand.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
                sendLoadGame(userGameCommand.getGameID(), session);
            }
            else { // Resign type
                resignCommand(session);
            }
        }
    }

    private enum UserType {
        WHITE,
        BLACK,
        OBSERVER
    }

    private UserType determineUserType(GameData data, String user) {
        UserType type;
        if (user.equals(data.whiteUsername())) {
            type = UserType.WHITE;
        }
        else if (user.equals(data.blackUsername())) {
            type = UserType.BLACK;
        }
        else { // Observer
            type = UserType.OBSERVER;
        }
        return type;
    }


    private void connectCommand(Integer gameID, AuthData userData, Session session) {
        // Sends Load_Game message to root client
        // Sends a Notification to all other clients in game that the root client connected, either as a player
        // (in which case their color must be specified) or as an observer.
        connections.add(gameID, session);
        String message;
        ChessGame game;
        try {
            GameData gameData = gameService.getGame(gameID);
            if (gameData == null) {
                ErrorMessage errorMessage = new ErrorMessage("Game Not Found");
                sendErrorMessage(new Gson().toJson(errorMessage), session);
                return;
            }
            else {
                game = gameData.game();
                String user = userData.username();
                UserType type = determineUserType(gameData, user);
                message = user.toUpperCase() + " connected to the game as " + type.toString();
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        LoadGameMessage loadGameMessage = new LoadGameMessage(game);
        NotificationMessage notificationMessage = new NotificationMessage(message);
        try {
            // Send load_game message to root client
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));
            // Send notification message to other clients
            connections.broadcast(gameID, session, new Gson().toJson(notificationMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean playersTurn(GameData gameData, UserType userType) {
        ChessGame.TeamColor currentTurn = gameData.game().getTeamTurn();
        if (userType == UserType.WHITE && currentTurn == ChessGame.TeamColor.WHITE) {
            return true;
        }
        else if (userType == UserType.BLACK && currentTurn == ChessGame.TeamColor.BLACK) {
            return true;
        }
        return false;
    }

    private void makeMoveCommand(MakeMoveCommand moveCommand, Session session) {
        GameData gameData;
        String username;
        try {
            gameData = gameService.getGame(moveCommand.getGameID());
            username = authService.getAuth(moveCommand.getAuthToken()).username();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        UserType userType = determineUserType(gameData, username);
        if (userType == UserType.OBSERVER) {
            sendErrorMessage("You Cannot Move Pieces As An OBSERVER", session);
            return;
        }
        else if (!playersTurn(gameData, userType)) {
            sendErrorMessage("Not Your Turn", session);
            return;
        }
        // Check to make sure valid move
        // Update game in database
        try {
            gameService.makeMove(moveCommand.getMoveCommand(), gameData.game(), moveCommand.getGameID());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        } catch (InvalidMoveException e) {
            sendErrorMessage("Invalid Move", session);
            return;
        }
        // Send notification to all clients but root client
        String message = "Move made: " + moveCommand.getMoveCommand().toString().substring(0,14);
        NotificationMessage notification = new NotificationMessage(message);
        // Send load_game to players
        try {
            LoadGameMessage loadGameMessage = new LoadGameMessage(gameService.getGame(moveCommand.getGameID()).game());
            connections.broadcast(moveCommand.getGameID(), null, new Gson().toJson(loadGameMessage));
            connections.broadcast(moveCommand.getGameID(), null, new Gson().toJson(notification));
        } catch (DataAccessException | IOException e) {
            throw new RuntimeException(e);
        }
        // If move results in check, checkmate, or stalemate send notification to all clients
    }

    private void leaveCommand(UserGameCommand command, Session session) {
        // If a player is leaving, then the game is updated to remove the root client. Game is updated in the database.
        try {
            AuthData rootUser = authService.getAuth(command.getAuthToken());
            GameData gameData = gameService.getGame(command.getGameID());
            UserType type = determineUserType(gameData, rootUser.username());
            if (type == UserType.WHITE) {
                gameService.leaveGame(gameData.gameID(),ChessGame.TeamColor.WHITE ,command.getAuthToken());
            }
            else if (type == UserType.BLACK) {
                gameService.leaveGame(gameData.gameID(), ChessGame.TeamColor.BLACK ,command.getAuthToken());
            }
            String message = rootUser.username().toUpperCase() + " has left the game";
            connections.remove(gameData.gameID(), session);
            // Send notification to all clients (including observers) except Root Client that player left game
            NotificationMessage notificationMessage = new NotificationMessage(message);
            connections.broadcast(gameData.gameID(), session, new Gson().toJson(notificationMessage));
        } catch (DataAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void resignCommand(Session session) {
        // Server marks the game as over (no more moves can be made). Game is updated in the database.
        // Send notification to all clients (including observers) that Root Client resigned and game is over
    }

    // SEND SERVER MESSAGES

    private void sendLoadGame(int gameID, Session session) {
        // Serialize Load_Game Message
        GameData gameData = null;
        try {
            gameData = this.gameService.getGame(gameID);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        LoadGameMessage message = new LoadGameMessage(gameData.game());
        // Send to client
        try {
            session.getRemote().sendString(new Gson().toJson(message));
        } catch (IOException e) {
            throw new WebSocketException(e);
        }
    }

    private void sendErrorMessage(String message, Session session) {
        ErrorMessage errorMessage = new ErrorMessage(message);
        try {
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
