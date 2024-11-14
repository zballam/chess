package ui;

import com.google.gson.Gson;
import model.GameData;
import model.GamesList;
import net.ServerFacade;

import java.util.Arrays;

public class loggedInClient {
    private static final Gson GSON = new Gson();
    ServerFacade serverFacade;
    String username;
    String authToken;

    public loggedInClient(ServerFacade serverFacade, String username, String authToken) {
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
                case "list" -> listGames();
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
        String result = serverFacade.logout(this.authToken);
        return logOutMessage(result);
    }

    private String logOutMessage(String result) {
        if (result.equals("{}")) {
            result = "You have successfully logged out";
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
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

    private String listGamesMessage(String result) {
        GamesList gamesList = GSON.fromJson(result, GamesList.class);
        StringBuilder builder = new StringBuilder();
        for (GameData game : gamesList.games()) {
            builder.append("- ");
            builder.append(game.gameID()).append(" ");
            builder.append(game.gameName()).append(" ");
            builder.append("\n").append("    ");
            builder.append("White: ").append(game.whiteUsername()).append(" ");
            builder.append("\n").append("    ");
            builder.append("Black: ").append(game.blackUsername());
            builder.append("\n");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public String listGames() {
        String result = serverFacade.listGames(this.authToken);
        return listGamesMessage(result);
    }

    public String playGame() {
        throw new RuntimeException("Not implemented yet");
    }

    public String observeGame() {
        throw new RuntimeException("Not implemented yet");
    }
}
