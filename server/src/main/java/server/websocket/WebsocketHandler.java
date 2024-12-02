package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import service.AuthService;
import service.GameService;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final GameService gameService;
    private final AuthService authService;
    private AuthData rootAuthData;

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
            makeMoveCommandHandler(session, (MakeMoveCommand) userGameCommand);
        }
        // Otherwise...
        else { // UserGameCommand class
            if (userGameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                try {
                    this.rootAuthData = authService.getAuth(userGameCommand.getAuthToken());
                } catch (DataAccessException e) {
                    throw new RuntimeException(e);
                }
                connectCommand(userGameCommand.getGameID(), session);
            }
            else if (userGameCommand.getCommandType() == UserGameCommand.CommandType.LEAVE) {
                leaveCommand(session);
            }
            else { // Resign type
                resignCommand(session);
            }
        }
    }

    private void makeMoveCommandHandler(Session session, MakeMoveCommand makeMoveCommand) {
        if (makeMoveCommand.getMoveCommand() == null) {
            sendLoadGame(session);
        }
        else {
            makeMoveCommand(session);
        }
    }


    private void connectCommand(Integer gameID, Session session) {
        // Sends Load_Game message to root client
        // Sends a Notification to all other clients in game that the root client connected, either as a player
        // (in which case their color must be specified) or as an observer.
        connections.add(gameID, session);
        String message;
        ChessGame game;
        try {
            GameData gameData = gameService.getGame(gameID);
            if (gameData == null) {
                game = null;
                message = "No game was found";
            }
            else {
                game = gameData.game();
                String teamColor;
                String rootUser = rootAuthData.username();
                if (rootUser.equals(gameData.whiteUsername())) {
                    teamColor = "WHITE";
                }
                else if (rootUser.equals(gameData.blackUsername())) {
                    teamColor = "BLACK";
                }
                else { // Observer
                    teamColor = "OBSERVER";
                }
                message = rootUser.toUpperCase() + " connected to the game as " + teamColor;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        try {
            // Send load_game message to root client
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));
            // Send notification message to other clients
            connections.broadcast(gameID, session, new Gson().toJson(notificationMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeMoveCommand(Session session) {
        // Check to make sure valid move
        // Update game in database
        // Send load_game to players
        // Send notification to all clients but root client
        // If move results in check, checkmate, or stalemate send notification to all clients
    }

    private void leaveCommand(Session session) {
        // If a player is leaving, then the game is updated to remove the root client. Game is updated in the database.
        // Send notification to all clients (including observers) except Root Client that player left game
    }

    private void resignCommand(Session session) {
        // Server marks the game as over (no more moves can be made). Game is updated in the database.
        // Send notification to all clients (including observers) that Root Client resigned and game is over
    }

    private void sendLoadGame(Session session) {
        // Serialize Load_Game Message
//        LoadGameMessage message = new LoadGameMessage()
            // Send to client
    }

    private void sendError(Session session) {
        // Serialize Error Message
        // Send to client
    }

    private void sendNotification(Session session) {
        // Serialize Notification Message
        // Send to client
    }
}
