package ui;

import java.util.Arrays;

public class logoutClient {

    public String run (String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        try {
            return switch (cmd) {
//                case "signin" -> signIn(params);
//                case "list" -> listPets();
//                case "signout" -> signOut();
                case "quit" -> "quit";
                default -> "quit";//help();
            };
        } catch (RuntimeException e) {

        }
        return input;
    }
}
