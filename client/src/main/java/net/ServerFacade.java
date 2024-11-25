package net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

public class ServerFacade {
    private static final Gson GSON = new Gson();
    private final HttpCommunicator httpCommunicator;

    public ServerFacade(String serverUrl) {
        this.httpCommunicator = new HttpCommunicator(serverUrl);
    }

    public String register(String username, String password, String email) {
        Map<String, String> registerReq = Map.of(
                "username", username,
                "password", password,
                "email", email
        );
        try {
            return httpCommunicator.register(GSON.toJson(registerReq));
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
            return httpCommunicator.login(GSON.toJson(loginReq));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String logout(String authToken) {
        try {
            return httpCommunicator.logout(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createGame(String gameName, String authToken) {
        Map<String, String> createGameReq = Map.of(
                "gameName", gameName
        );
        try {
            return httpCommunicator.createGame(GSON.toJson(createGameReq), authToken);
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
            return httpCommunicator.joinGame(GSON.toJson(joinGameReq), authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
