package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.*;

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

}
