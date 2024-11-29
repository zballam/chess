package server.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        Gson customDeserializer = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer()).create();
        var userGameCommand = customDeserializer.fromJson(message, UserGameCommand.class);
        if (userGameCommand instanceof MakeMoveCommand) { // MakeMoveCommand class
            makeMoveCommand(session);
        }
        else { // UserGameCommand class
            if (userGameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT) {
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


    private void connectCommand(Integer gameID, Session session) {
        connections.add(gameID, session);
        // Send load_game message to root client
        // Sends a Notification to all other clients in game that the root client connected, either as a player
        // (in which case their color must be specified) or as an observer.
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
