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
                case "draw" -> draw();
                case "leave" -> leave();
                case "redraw" -> redraw();
                case "move" -> move();
                case "highlight" -> highlight();
                case "resign" -> resign();
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
                - redraw (redraws the current board)
                - highlight (highlights valid moves)
                - move
                - leave
                - resign
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

    private String leave() {
        return "Leave Game";
    }

    private String redraw() {
        throw new RuntimeException("Not implemented");
    }

    private String move(){
        throw new RuntimeException("Not implemented");
    }

    private String highlight() {
        throw new RuntimeException("Not implemented");
    }

    private String resign() {
        throw new RuntimeException("Not implemented");
    }
}