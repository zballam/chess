package net;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AuthData;

import java.io.IOException;
import java.util.Map;

public class ServerFacade {
    private static final Gson GSON = new Gson();
    private final HttpCommunicator httpCommunicator;
    private final WebsocketCommunicator websocketCommunicator;
    private String authToken = "";
    private Integer gameID = 0;

    public ServerFacade(String serverUrl, MessageObserver messageObserver) {
        this.httpCommunicator = new HttpCommunicator(serverUrl);
        this.websocketCommunicator = new WebsocketCommunicator(serverUrl, messageObserver);
    }

    private String extractAuthToken(String json) {
        var httpResponse = new Gson().fromJson(json, AuthData.class);
        return httpResponse.authToken();
    }

    private Integer extractGameID(String json) {
        var httpResponse = new Gson().fromJson(json, JsonObject.class);
        if (httpResponse.has("gameID")) {
            return httpResponse.get("gameID").getAsInt();
        }
        return 0;
    }

    public String register(String username, String password, String email) {
        Map<String, String> registerReq = Map.of(
                "username", username,
                "password", password,
                "email", email
        );
        try {
            var response = httpCommunicator.register(GSON.toJson(registerReq));
            this.authToken = extractAuthToken(response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String login(String username, String password) {
        Map<String, String> loginReq = Map.of(
                "username", username,
                "password", password
        );
        try {
            var response = httpCommunicator.login(GSON.toJson(loginReq));
            this.authToken = extractAuthToken(response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String logout(String authToken) {
        try {
            var response = httpCommunicator.logout(authToken);
            this.authToken = "";
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createGame(String gameName, String authToken) {
        Map<String, String> createGameReq = Map.of(
                "gameName", gameName
        );
        try {
            var response = httpCommunicator.createGame(GSON.toJson(createGameReq), authToken);
            this.gameID = extractGameID(response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String listGames(String authToken) {
        try {
            return httpCommunicator.listGames(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String joinGame(String gameID, String playerColor, String authToken) {
        Map<String, String> joinGameReq = Map.of(
                "playerColor", playerColor.toUpperCase(),
                "gameID", gameID
        );
        try {
            var response = httpCommunicator.joinGame(GSON.toJson(joinGameReq), authToken);
//            this.gameID = extractGameID(response);
            this.gameID = Integer.valueOf(gameID);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // WebSocket Methods

    public void connectWS() {
        websocketCommunicator.connect(this.authToken, this.gameID);
    }

    public void makeMoveWS() {
        websocketCommunicator.makeMoveWS();
    }

    public void redraw() {
        this.websocketCommunicator.redrawRequest(this.authToken, this.gameID);
    }
}
