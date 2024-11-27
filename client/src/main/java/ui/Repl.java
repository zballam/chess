package ui;

import net.MessageObserver;
import net.ServerFacade;
import websocket.messages.ServerMessage;

import java.util.Objects;
import java.util.Scanner;

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

    public Repl(String url) {
        this.serverFacade = new ServerFacade(url, this);
        this.logoutREPL = new LoggedOutClient(serverFacade);
        this.gameREPL = new GameClient(serverFacade);
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
                result = "Starting Game...";
                this.state = State.INGAME;
                gameREPL.run("draw");
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
    public void notify(ServerMessage notification) {
        // Call functions based on message type
        switch (notification.getServerMessageType()) {
            case LOAD_GAME -> notifyLoadGameMessage();
            case ERROR -> notifyErrorMessage();
            case NOTIFICATION -> notifyNotificationMessage();
        }
//                    // Add a GSON deserializer LOOK IT UP
        // Look up type cast. Will need to type cast the message
    }

    private void notifyLoadGameMessage() {}

    private void notifyErrorMessage() {}

    private void notifyNotificationMessage() {}
}
