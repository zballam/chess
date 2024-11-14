package ui;

import com.google.gson.Gson;
import model.AuthData;
import net.ServerFacade;

import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final ServerFacade serverFacade;
    private final logoutClient logoutREPL;
    private loginClient loginREPL = null;
    private final gameClient gameREPL;
    private static final Gson GSON = new Gson();
    private State state;
    private String username = "";
    private String authToken = "";
    private static final String MENUCOLOR = SET_TEXT_COLOR_MAGENTA;

    public Repl(String url) {
        this.serverFacade = new ServerFacade(url);
        this.logoutREPL = new logoutClient(serverFacade);
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

    /**
     * Creates the result string based on the response rom the ServerFacade
     */
    private String logoutMessage(String result) {
        if (result.startsWith("{\"authToken\":")) {
            this.state = State.SIGNEDIN;
            AuthData newUser = GSON.fromJson(result, AuthData.class);
            // Initialize the loginClient
            this.username = newUser.username();
            this.authToken = newUser.authToken();
            this.loginREPL = new loginClient(serverFacade, this.username, this.authToken);
            result = "Welcome " + this.username.toUpperCase();
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
    }

    private String loginMessage(String result) {
        if (result.equals("{}")) {
            this.state = State.SIGNEDOUT;
            result = "You have successfully logged out";
        }
        else if (result.startsWith("{ \"message\":")) {
            result = result.substring(14,result.length()-3);
        }
        return result;
    }

    /**
     * Determines which commands to call based on the current state and command
     */
    public String eval(String line) {
        String result;
        if (state == State.SIGNEDOUT) {
            result = logoutREPL.run(line);
            result = logoutMessage(result);
        }
        else if (state == State.SIGNEDIN) {
            result = loginREPL.run(line);
            result = loginMessage(result);
        }
        else {
            result = gameREPL.run(line);
        }
        return result;
    }
}
