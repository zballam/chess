package net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

public class ServerFacade {
    private static final Gson GSON = new Gson();
    private final ClientCommunicator clientCommunicator;

    public ServerFacade(String serverUrl) {
        this.clientCommunicator = new ClientCommunicator(serverUrl);
    }

    public String register(String username, String password, String email) {
        Map<String, String> registerReq = Map.of(
                "username", username,
                "password", password,
                "email", email
        );
        try {
            return clientCommunicator.register(GSON.toJson(registerReq));
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
            return clientCommunicator.login(GSON.toJson(loginReq));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String logout(String authToken) {
        try {
            return clientCommunicator.logout(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createGame(String gameName, String authToken) {
        Map<String, String> createGameReq = Map.of(
                "gameName", gameName
        );
        try {
            return clientCommunicator.createGame(GSON.toJson(createGameReq), authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String listGames(String authToken) {
        try {
            return clientCommunicator.listGames(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
