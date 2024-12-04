package ui;

import chess.ChessPosition;
import net.MessageObserver;
import net.ServerFacade;
import websocket.messages.ErrorMessage;

import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.RESET_TEXT_COLOR;

public class GameClient {
    ServerFacade serverFacade;
    private final String MENUCOLOR;
    private boolean player;
    private final MessageObserver messageObserver;

    public GameClient(ServerFacade serverFacade, String menucolor, MessageObserver messageObserver) {
        this.serverFacade = serverFacade;
        this.MENUCOLOR = menucolor;
        this.messageObserver = messageObserver;
    }

    public void connectWS() {
        serverFacade.connectWS();
    }

    public void setPlayerVal(boolean player) {
        this.player = player;
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

    private int translate(String letter) {
        switch (letter.toUpperCase()) {
            case "A" -> {
                return 1;
            }
            case "B" -> {
                return 2;
            }
            case "C" -> {
                return 3;
            }
            case "D" -> {
                return 4;
            }
            case "E" -> {
                return 5;
            }
            case "F" -> {
                return 6;
            }
            case "G" -> {
                return 7;
            }
            case "H" -> {
                return 8;
            }
            default -> throw new IllegalArgumentException("Invalid letter: " + letter);
        }
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
            ChessPosition position1 = new ChessPosition(startPosition.charAt(1) - '0', translate(String.valueOf(startPosition.charAt(0))));
            ChessPosition position2 = new ChessPosition(endPosition.charAt(1) - '0', translate(String.valueOf(endPosition.charAt(0))));
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
        if (this.player) {
            System.out.print("\n" + MENUCOLOR + "Are You Sure You Want To Resign?" + RESET_TEXT_COLOR + "\n");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("Y") || line.equalsIgnoreCase("YES")) {
                serverFacade.resign();
            }
        }
        else {
            ErrorMessage errorMessage = new ErrorMessage("You Cannot Resign As An Observer");
            messageObserver.notify(errorMessage);
        }
        return "";
    }
}
