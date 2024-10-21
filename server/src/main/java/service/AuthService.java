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

    public String getAuthToken(String authToken) throws DataAccessException {
        return this.authDAO.getAuth(authToken).authToken();
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return this.authDAO.getAuth(authToken);
    }

    public void authenticate(AuthData auth) throws DataAccessException {
        AuthData authData = getAuth(auth.authToken());
        if (authData == null) { throw new DataAccessException("AuthToken doesn't exist"); }
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

    public void logout(AuthData auth) throws DataAccessException {
        authenticate(auth);
        AuthData authData = getAuth(auth.authToken());
        authDAO.deleteAuth(authData.authToken());
    }
}
