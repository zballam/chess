package ui;

import java.util.Objects;
import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {
    private final logoutClient logoutREPL;
    private final loginClient loginREPL;
    private final gameClient gameREPL;
    private State state;

    public Repl(String url) {
        logoutREPL = new logoutClient(url);
        loginREPL = new loginClient();
        gameREPL = new gameClient();
        state = State.SIGNEDOUT;
    }

    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        INGAME
    }

    public void run() {
        System.out.println("♕ Welcome to 240 Chess. Type HELP to begin. ♕");
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
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    public void notification(String notification) {
        System.out.print(SET_BG_COLOR_RED + notification);
        printPrompt();
    }

    public void printPrompt() {
        System.out.print(RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_YELLOW);
    }

    /**
     * Determines which commands to call based on the current state and command
     */
    public String eval(String line) {
        String result;
        if (state == State.SIGNEDOUT) {
            result = logoutREPL.run(line);
            if (result.startsWith("Expected:")) {
                state = State.SIGNEDIN;
            }
        }
        else if (state == State.SIGNEDIN) {
            result = loginREPL.run(line);
        }
        else {
            result = gameREPL.run(line);
        }
        return result;
    }
}
