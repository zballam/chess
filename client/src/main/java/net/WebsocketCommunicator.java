package net;

import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.*;

public class WebsocketCommunicator extends Endpoint {

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
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    notificationHandler.notify(notification);
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
}
