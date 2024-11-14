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
        return serverFacade.logout(this.username, this.authToken);
    }

    public String createGame() {
        throw new RuntimeException("Not implemented yet");
    }

    public String listGames() {
        throw new RuntimeException("Not implemented yet");
    }

    public String playGame() {
        throw new RuntimeException("Not implemented yet");
    }

    public String observeGame() {
        throw new RuntimeException("Not implemented yet");
    }
}
