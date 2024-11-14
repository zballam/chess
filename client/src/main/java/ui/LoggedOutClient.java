package ui;

import com.google.gson.Gson;
import model.AuthData;
import net.ServerFacade;

import java.util.Arrays;

public class LoggedOutClient {
    private static final Gson GSON = new Gson();
    ServerFacade serverFacade;

    public LoggedOutClient(ServerFacade serverFacade) {
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
                default -> error();
            };
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private String error() {
        return "ERROR: Invalid command";
    }

    public String help() {
        return """
                HELP MENU
                - register <USERNAME> <PASSWORD> <EMAIL>
                - login <USERNAME> <PASSWORD>
                - quit
                - help""";
    }

    private String message(String result) {
        if (result.startsWith("{\"authToken\":")) {
            AuthData newUser = GSON.fromJson(result, AuthData.class);
            // Initialize the loginClient
            String newUsername = newUser.username();
            String newAuthToken = newUser.authToken();
            result = "Welcome " + newUsername.toUpperCase() + " " + newAuthToken;
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
    }

    public String login(String[] params) {
        if (params.length == 2) {
            String username = params[0];
            String password = params[1];
            String result = serverFacade.login(username,password);
            return message(result);
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
            String result = serverFacade.register(username,password,email);
            return message(result);
        }
        else {
            throw new RuntimeException("Expected: <USERNAME> <PASSWORD> <EMAIL>");
        }
    }
}
