package net;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import websocket.commands.*;
import websocket.messages.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.*;

public class WebsocketCommunicator extends Endpoint {
    Session session;
    private ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;

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
                    if (responseMessage instanceof LoadGameMessage) {
                        notificationHandler.notifyLoadGameMessage((LoadGameMessage) responseMessage, teamColor);
                    }
                    notificationHandler.notify(responseMessage);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new WebsocketException(ex.getMessage());
        }
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void connect(String authToken, Integer gameID, ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
        var connectRequest = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(connectRequest));
        } catch (IOException e) {
            throw new WebsocketException(e.getMessage());
        }
    }

    public void redrawRequest(String authToken, Integer gameID) {
        MakeMoveCommand redrawCommand = new MakeMoveCommand(authToken, gameID, null);
        if (!this.session.isOpen()) {
            throw new RuntimeException("Session Closed early");
        }
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(redrawCommand));
        } catch (IOException e) {
            throw new WebsocketException(e.getMessage());
        }
    }

    public void leave(String authToken, int gameID) {
        UserGameCommand leaveCommand = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(leaveCommand));
//            this.session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMoveWS(String authToken, Integer gameID, ChessMove move) {
        MakeMoveCommand moveCommand = new MakeMoveCommand(authToken, gameID, move);
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(moveCommand));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
