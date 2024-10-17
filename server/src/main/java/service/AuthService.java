package service;

import dataaccess.AuthDAO;
import model.*;

public class AuthService {
    AuthDAO authDAO;

    public AuthService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public void clear() {
        throw new RuntimeException("Not implemented");
    }

    public AuthData getAuth(String authToken) {
        throw new RuntimeException("Not implemented");
    }

}
