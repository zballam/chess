package ui;

import static ui.EscapeSequences.*;

public class loginClient {

    public String run(String command) {
        System.out.print(SET_BG_COLOR_MAGENTA + "LOGGED IN:");
        if (!command.equals(null)) {
            throw new RuntimeException("LoginClient: Not implemented yet");
        }
        throw new RuntimeException("LoginClient");
    }

    public String help(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String logout(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String createGame(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String listGames(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String playGame(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }

    public String observeGame(String cmd) {
        throw new RuntimeException("Not implemented yet");
    }
}
