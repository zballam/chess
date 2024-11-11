package ui;

public class Client {
    State state = State.SIGNEDOUT;

    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        INGAME
    }

    /**
     * Determines which commands to call based on the current state and command
     */
    public void eval() {
        if (state == State.SIGNEDOUT) {
            logoutREPL();
        }
        else if (state == State.SIGNEDIN) {
            loginREPL();
        }
        else {
            gameREPL();
        }
    }

    /**
     * Loop through the logged out menu
     */
    public void logoutREPL() {

    }

    /**
     * Loop through the logged in menu
     */
    public void loginREPL() {

    }

    /**
     * Loop through the in game menu
     */
    public void gameREPL() {

    }
}
