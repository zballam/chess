package server.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        Gson customDeserializer = new GsonBuilder().registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer()).create();
        var userGameCommand = customDeserializer.fromJson(message, UserGameCommand.class);
        if (userGameCommand instanceof MakeMoveCommand) {
            throw new RuntimeException("MakeMoveCommand received");
        }
        else { // UserGameCommand class
            if (userGameCommand.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                connectCommand(userGameCommand.getGameID(), session);
            }
            else if (userGameCommand.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
                throw new RuntimeException("Make Move type deserialized");
            }
            else if (userGameCommand.getCommandType() == UserGameCommand.CommandType.LEAVE) {
                throw new RuntimeException("Leave type deserialized");
            }
            else { // Resign type
                throw new RuntimeException("Resign type deserialized");
            }
        }
    }


    private void connectCommand(Integer gameID, Session session) {
        connections.add(gameID, session);
    }
}
