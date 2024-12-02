package net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ui.BoardDrawer;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.*;

public class WebsocketCommunicator extends Endpoint {
    private BoardDrawer drawer = new BoardDrawer();
    Session session;

    public WebsocketCommunicator(String url, MessageObserver notificationHandler) {
        try {
            url = url.replace("http","ws");
            URI socketURI = new URI(url + "/ws");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    // Define customDeserializer for ServerMessages
                    Gson customDeserializer = new GsonBuilder().registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer()).create();
                    var responseMessage = customDeserializer.fromJson(message, ServerMessage.class);
                    // Call correct response function depending on message class
                    if (responseMessage instanceof LoadGameMessage) {
                        loadGameResponse((LoadGameMessage) responseMessage);
                    }
                    else if (responseMessage instanceof NotificationMessage) {
                        notificationHandler.notify(responseMessage);
                    }
//                    else if (responseMessage instanceof ErrorMessage) {
//
//                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new WebsocketException(ex.getMessage());
        }
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void connect(String authToken, Integer gameID) {
        var connectRequest = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(connectRequest));
        } catch (IOException e) {
            throw new WebsocketException(e.getMessage());
        }
    }

    public void makeMoveWS() {

    }

    public void redrawRequest(String authToken, Integer gameID) {
        MakeMoveCommand redrawCommand = new MakeMoveCommand(authToken, gameID, null);
        try {
            session.getBasicRemote().sendText(new Gson().toJson(redrawCommand));
        } catch (IOException e) {
            throw new WebsocketException(e.getMessage());
        }
    }

    // RESPONSE METHODS

    private void loadGameResponse(LoadGameMessage responseMessage) {
        throw new WebsocketException("IT WORKS!!");
    }
}
