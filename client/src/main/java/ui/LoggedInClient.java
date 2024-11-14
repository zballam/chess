package ui;

import com.google.gson.Gson;
import model.GameData;
import model.GamesList;
import net.ServerFacade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoggedInClient {
    private static final Gson GSON = new Gson();
    private final ServerFacade serverFacade;
    private final String authToken;
    Map<String, String> gameIDs;
    Map<String, String> gameIDsInverse;

    public LoggedInClient(ServerFacade serverFacade, String authToken) {
        this.serverFacade = serverFacade;
        this.authToken = authToken;
        this.gameIDs = new HashMap<>();
        this.gameIDsInverse = new HashMap<>();
        listGames();
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
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
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

    private String logOutMessage(String result) {
        if (result.equals("{}")) {
            result = "You have successfully logged out";
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
    }

    public String logout() {
        String result = serverFacade.logout(this.authToken);
        return logOutMessage(result);
    }

    private String createGameMessage(String result) {
        if (result.startsWith("{ \"gameID\": ")) {
            String gameID = result.substring(12,result.length()-2);
            listGames();
            result = "Successfully created game with gameID: " + this.gameIDsInverse.get(gameID);
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
    }

    public String createGame(String[] params) {
        if (params.length == 1) {
            String gameName = params[0];
            String result = serverFacade.createGame(gameName, this.authToken);
            return createGameMessage(result);
        }
        else {
            throw new RuntimeException("Expected: <NAME>");
        }
    }

    private String listGamesMessage(String result) {
        try {
            GamesList gamesList = GSON.fromJson(result, GamesList.class);
            StringBuilder builder = new StringBuilder();
            Integer x = 1;
            this.gameIDs.clear();
            this.gameIDsInverse.clear();
            for (GameData game : gamesList.games()) {
                builder.append("- ");
                this.gameIDs.put(x.toString(), String.valueOf(game.gameID()));
                this.gameIDsInverse.put(String.valueOf(game.gameID()), x.toString());
                builder.append(x).append(" ");
                builder.append(game.gameName()).append(" ");
                builder.append("\n").append("    ");
                builder.append("White: ").append(game.whiteUsername()).append(" ");
                builder.append("\n").append("    ");
                builder.append("Black: ").append(game.blackUsername());
                builder.append("\n");
                x++;
            }
            builder.deleteCharAt(builder.length() - 1);
            result = builder.toString();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Index -1 out of bounds for length 0")) {
                return "No games are currently created";
            }
            else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public String listGames() {
        String result = serverFacade.listGames(this.authToken);
        return listGamesMessage(result);
    }

    private String joinGameMessage(String result, String gameID, String teamColor) {
        if (result.equals("{}")) {
            result = "You have successfully joined game " + gameID + " as " + teamColor;
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
    }

    public String joinGame(String[] params) {
        if (params.length == 2) {
            String gameID = this.gameIDs.get(params[0]);
            String playerColor = params[1];
            String result = serverFacade.joinGame(gameID, playerColor, this.authToken);
            return joinGameMessage(result, params[0], playerColor);
        }
        else {
            throw new RuntimeException("Expected: <ID> [WHITE|BLACK]");
        }
    }

    public String observeGame(String[] params) {
        if (params.length == 1) {
            String gameID = gameIDs.get(params[0]);
        }
        else {
            throw new RuntimeException("Expected: <ID>");
        }
        return "Start Game";
    }
}
