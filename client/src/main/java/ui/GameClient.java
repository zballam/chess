package ui;

import chess.ChessGame;
import net.MessageObserver;
import net.ServerFacade;
import websocket.messages.ServerMessage;

import java.util.Arrays;

public class GameClient {
    ServerFacade serverFacade;

    public GameClient(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    public void connectWS() {
        serverFacade.connectWS();
    }

    public String run(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return switch(cmd) {
                case "help" -> help();
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

    private String leave() {
        return "Leave Game";
    }

    private String redraw() {
        serverFacade.redraw();
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
