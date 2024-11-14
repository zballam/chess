package ui;

import com.google.gson.Gson;
import net.ServerFacade;

import java.util.Arrays;

public class logoutClient {
    ServerFacade serverFacade;

    public logoutClient(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

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
                HELP MENU
                - register <USERNAME> <PASSWORD> <EMAIL>
                - login <USERNAME> <PASSWORD>
                - quit
                - help""";
    }

    public String login(String[] params) {
        if (params.length == 2) {
            String username = params[0];
            String password = params[1];
            return serverFacade.login(username,password);
        }
        else {
            throw new RuntimeException("Expected: <USERNAME> <PASSWORD>");
        }
    }

    public String register(String[] params) {
        if (params.length == 3) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            return serverFacade.register(username,password,email);
        }
        else {
            throw new RuntimeException("Expected: <USERNAME> <PASSWORD> <EMAIL>");
        }
    }
}
