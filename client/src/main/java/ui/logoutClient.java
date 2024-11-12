package ui;

import java.util.Arrays;

public class logoutClient {

    public String run(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return switch(cmd) {
                case "login" -> login(params);
                case "help" -> help();
                case "register" -> register(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String help() {
        return """
                - login
                - register
                - help
                """;
    }

    public String login(String[] params) {
        throw new RuntimeException("Not implemented yet");
    }

    public String register(String[] params) {
        throw new RuntimeException("Not implemented yet");
    }
}
