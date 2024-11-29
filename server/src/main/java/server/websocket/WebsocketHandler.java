package server.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        Gson customDeserializer = new GsonBuilder().registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer()).create();
        var serverMessage = customDeserializer.fromJson(message, ServerMessage.class);
        switch (serverMessage.getClass()) {
//            case LoadGameMessage.class ->
//            case ERROR ->
//            case NOTIFICATION ->
            default -> throw new IllegalStateException("Unexpected value: " + serverMessage.getClass());
        }
//        throw new RuntimeException("onMessage not implemented");
    }


}
