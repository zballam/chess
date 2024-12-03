package ui;

import chess.ChessGame;
import chess.ChessPosition;
import net.MessageObserver;
import net.ServerFacade;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
                case "move" -> move(params);
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
                - move <START POSITION> <END POSITION>
                - leave
                - resign
                - quit
                - help""";
    }

    private String leave() {
        serverFacade.leaveWS();
        return "Leave Game";
    }

    private String redraw() {
        serverFacade.redraw();
        return "";
    }

    private boolean verifyMoveInput(String input) {
        return input.matches("^[a-hA-H][1-8]$");
    }

    private String move(String[] params){
        if (params.length == 2) {
            String startPosition = params[0];
            String endPosition = params[1];
            // Check to make sure startPosition and endPosition are valid formats
            if (!verifyMoveInput(startPosition)) {
                return "Expected: Valid Start Position Format: i.e. A1";
            }
            if (!verifyMoveInput(endPosition)) {
                return "Expected: Valid End Position Format: i.e. A1";
            }
            ChessPosition position1 = new ChessPosition(startPosition.charAt(0), startPosition.charAt(1));
            ChessPosition position2 = new ChessPosition(endPosition.charAt(0), endPosition.charAt(1));
            serverFacade.makeMoveWS(position1,position2);
            return "";
        }
        else {
            throw new RuntimeException("Expected: <START POSITION> <END POSITION>");
        }
    }

    private String highlight() {
        throw new RuntimeException("Not implemented");
    }

    private String resign() {
        throw new RuntimeException("Not implemented");
    }
}
