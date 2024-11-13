package ui;

import java.util.Arrays;

import static ui.EscapeSequences.*;

public class loginClient {

    public String run(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return switch(cmd) {
                case "help" -> help();
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

    public String logout(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String createGame(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String listGames(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String playGame(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String observeGame(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }
}
