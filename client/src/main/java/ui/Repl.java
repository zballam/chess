package ui;

import net.ServerFacade;

import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final ServerFacade serverFacade;
    private final loggedOutClient logoutREPL;
    private loggedInClient loginREPL = null;
    private final gameClient gameREPL;
    private State state;
    private String username = "";
    private String authToken = "";
    private static final String MENUCOLOR = SET_TEXT_COLOR_MAGENTA;

    public Repl(String url) {
        this.serverFacade = new ServerFacade(url);
        this.logoutREPL = new loggedOutClient(serverFacade);
        this.gameREPL = new gameClient(serverFacade);
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

    public void notification(String notification) {
        System.out.print(SET_BG_COLOR_RED + notification);
        printPrompt();
    }

    public void printPrompt() {
        if (this.state == State.SIGNEDOUT) {
            System.out.print(MENUCOLOR + "[LOGGED OUT] >>> " + RESET_TEXT_COLOR);
        }
        else if (state == State.SIGNEDIN) {
            System.out.print(MENUCOLOR + "[" + this.username.toUpperCase() + "] >>> " + RESET_TEXT_COLOR);
        }
    }

    private String setStateSignedIn(String result) {
        this.state = Repl.State.SIGNEDIN;
        // Initialize the loginClient
        var tokens = result.toLowerCase().split(" ");
        this.username = tokens[1];
        this.authToken = tokens[2];
        this.loginREPL = new loggedInClient(serverFacade, this.username, this.authToken);
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
            else if (result.equals("Start Game")) {
                this.state = State.INGAME;
            }
        }
        else {
            result = gameREPL.run(line);
        }
        return result;
    }
}
