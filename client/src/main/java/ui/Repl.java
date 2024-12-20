package ui;

import chess.ChessGame;
import chess.ChessMove;
import net.MessageObserver;
import net.ServerFacade;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.*;

import static ui.EscapeSequences.*;

public class Repl implements MessageObserver {
    private final ServerFacade serverFacade;
    private final LoggedOutClient logoutREPL;
    private LoggedInClient loginREPL = null;
    private final GameClient gameREPL;
    private State state;
    private String username = "";
    private String authToken = "";
    private static final String MENUCOLOR = SET_TEXT_COLOR_MAGENTA;
    private static final String NOTIFICATIONCOLOR = SET_TEXT_COLOR_YELLOW;
    private static final String ERRORCOLOR = SET_TEXT_COLOR_RED;

    public Repl(String url) {
        this.serverFacade = new ServerFacade(url, this);
        this.logoutREPL = new LoggedOutClient(serverFacade);
        this.gameREPL = new GameClient(serverFacade, MENUCOLOR, this);
        this.state = State.SIGNEDOUT;
    }

    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        INGAME
    }

    public void run() {
        System.out.println(MENUCOLOR + "♕ Welcome to 240 Chess. Type HELP to begin. ♕" + RESET_TEXT_COLOR);
        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();
            try {
                result = eval(line);
                if (!Objects.equals(result, "quit")) {
                    System.out.println(SET_TEXT_COLOR_BLUE + result);
                }
            } catch (Throwable e) {
                String msg = e.toString();
                System.out.println(msg);
            }
        }
        System.out.println();
    }

    public void printPrompt() {
        if (this.state == State.SIGNEDOUT) {
            System.out.print(MENUCOLOR + "[LOGGED OUT] >>> " + RESET_TEXT_COLOR);
        }
        else if (state == State.SIGNEDIN) {
            System.out.print(MENUCOLOR + "[" + this.username.toUpperCase() + "] >>> " + RESET_TEXT_COLOR);
        }
        else {
            System.out.print(MENUCOLOR + "[IN GAME] >>> " + RESET_TEXT_COLOR);
        }
    }

    private String setStateSignedIn(String result) {
        this.state = Repl.State.SIGNEDIN;
        // Initialize the loginClient
        var tokens = result.toLowerCase().split(" ");
        this.username = tokens[1];
        this.authToken = tokens[2];
        this.loginREPL = new LoggedInClient(serverFacade, this.authToken);
        return "Welcome " + this.username.toUpperCase();
    }

    /**
     * Determines which commands to call based on the current state and command
     */
    public String eval(String line) {
        String result;
        if (state == State.SIGNEDOUT) {
            result = logoutREPL.run(line);
            if (result.startsWith("Welcome ")) {
                result = setStateSignedIn(result);
            }
        }
        else if (state == State.SIGNEDIN) {
            result = loginREPL.run(line);
            if (result.equals("You have successfully logged out")) {
                this.state = State.SIGNEDOUT;
            }
            else if (result.equals("Start Game") || result.startsWith("You have successfully joined game ")) {
                if (result.startsWith("You have successfully joined game ")) {
                    gameREPL.setPlayerVal(true);
                }
                else if (result.equals("Start Game")){
                    gameREPL.setPlayerVal(false);
                }
                result = "Starting Game...";
                this.state = State.INGAME;
                gameREPL.connectWS();
            }
        }
        else {
            result = gameREPL.run(line);
            if (result.equals("Leave Game")) {
                result = "Leaving Game...";
                this.state = State.SIGNEDIN;
            }
        }
        return result;
    }

    @Override
    public void notify(ServerMessage message) {
        // Call functions based on message type
        if (message instanceof NotificationMessage) {
            notifyNotificationMessage((NotificationMessage) message);
        }
        else if (message instanceof ErrorMessage) {
            notifyErrorMessage((ErrorMessage) message);
        }
        else {
            throw new RuntimeException("Invalid type of Server Message received");
        }
    }

    @Override
    public void notifyLoadGameMessage(LoadGameMessage message, ChessGame.TeamColor teamColor) {
        ChessGame game = message.getGame();
        if (message.getLastMove() != null) {
            Collection<ChessMove> highlightMove = new ArrayList<>();
            highlightMove.add(message.getLastMove());
            BoardDrawer.highlightMoves(highlightMove, game.getBoard().getSquares(), teamColor);
        }
        else {
            BoardDrawer.drawChessBoard(game.getBoard().getSquares(), teamColor);
        }
        printPrompt();
    }

    private void notifyNotificationMessage(NotificationMessage message) {
        System.out.print("\n" + NOTIFICATIONCOLOR + message.getMessage() + RESET_TEXT_COLOR + "\n");
        printPrompt();
    }

    private void notifyErrorMessage(ErrorMessage message) {
        System.out.print("\n" + ERRORCOLOR + message.getErrorMessage() + RESET_TEXT_COLOR + "\n");
        printPrompt();
    }
}
