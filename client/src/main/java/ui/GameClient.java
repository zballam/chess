package ui;

import chess.ChessGame;
import net.ServerFacade;

import java.util.Arrays;

public class GameClient {
    ServerFacade serverFacade;
    private final BoardDrawer drawer = new BoardDrawer();

    public GameClient(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    public String run(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return switch(cmd) {
                case "help" -> help();
                case "draw" -> draw(params);
                case "leave" -> leave();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private String leave() {
        return "Leave Game";
    }

    public String help() {
        return """
                HELP MENU
                - draw
                - leave
                - quit
                - help""";
    }

    public String draw() {
        // This is all temporary. For now just print the boards
        ChessGame game = new ChessGame();
        drawer.drawChessBoard(game.getBoard().getSquares(), ChessGame.TeamColor.WHITE);
        drawer.drawChessBoard(game.getBoard().getSquares(), ChessGame.TeamColor.BLACK);
        return "Board drawn...";
    }
}
