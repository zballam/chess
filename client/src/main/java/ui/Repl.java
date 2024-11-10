package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {
    private final Client client = new Client();

    public void run() {
        System.out.println("♕ Welcome to 240 Chess. Type HELP to begin. ♕");
        Scanner scanner = new Scanner(System.in);
        String result = "";

        while (!result.equals("quit") || !result.equals("q")) {

        }
    }

    public void notification() {

    }

    public void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_YELLOW);
    }
}
