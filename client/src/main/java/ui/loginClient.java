package ui;

import net.ServerFacade;

import java.util.Arrays;

public class loginClient {
    ServerFacade serverFacade;
    String username;
    String authToken;

    public loginClient(ServerFacade serverFacade, String username, String authToken) {
        this.serverFacade = serverFacade;
        this.username = username;
        this.authToken = authToken;
    }

    public String run(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return switch(cmd) {
                case "help" -> help();
                case "logout" -> logout();
                case "create" -> createGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String help() {
        return """
                HELP MENU
                - logout
                - create <NAME>
                - list
                - join <ID> [WHITE|BLACK]
                - observe <ID>
                - quit
                - help""";
    }

    public String logout() {
        return serverFacade.logout(this.authToken);
    }

    public String createGame(String[] params) {
        if (params.length == 1) {
            String gameName = params[0];
            return serverFacade.createGame(gameName, this.authToken);
        }
        else {
            throw new RuntimeException("Expected: <NAME>");
        }
    }

    public String listGames() {
        return serverFacade.listGames(this.authToken);
    }

    public String playGame() {
        throw new RuntimeException("Not implemented yet");
    }

    public String observeGame() {
        throw new RuntimeException("Not implemented yet");
    }
}
