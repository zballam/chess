package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.*;

import java.util.Random;

public class AuthService {
    AuthDAO authDAO;

    public AuthService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public void clear() {
        try {
            authDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData getAuth(String authToken) {
        throw new RuntimeException("Not implemented");
    }

    private String randGenString(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(rand.nextInt(chars.length()));
        }
        return builder.toString();
    }

    public AuthData createAuth(UserData user) throws DataAccessException {
        AuthData authData = new AuthData(randGenString(10), user.username());
        authDAO.createAuth(authData);
        return authData;
    }

}
