package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {
    private final logoutClient logoutREPL;
    private final loginClient loginREPL;
    private final gameClient gameREPL;
    private State state;

    public Repl() {
        logoutREPL = new logoutClient();
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

        while (!result.equals("quit") || !result.equals("q")) {
            printPrompt();
            String line = scanner.nextLine();

            result = "test";
            System.out.print(SET_TEXT_COLOR_BLUE + result);
        }
    }

    public void notification(String notification) {
        System.out.println(SET_BG_COLOR_RED + notification);
        printPrompt();
    }

    public void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_YELLOW);
    }

    /**
     * Determines which commands to call based on the current state and command
     */
    public void eval() {
        if (state == State.SIGNEDOUT) {
            logoutREPL.run();
        }
        else if (state == State.SIGNEDIN) {
            loginREPL.run();
        }
        else {
            gameREPL.run();
        }
    }
}
