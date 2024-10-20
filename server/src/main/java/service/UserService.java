package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;

public class UserService {
    UserDAO userDAO;
    AuthService authService;

    public UserService(UserDAO userDAO, AuthService authService) {
        this.userDAO = userDAO;
        this.authService = authService;
    }

    public void clear() {
        try {
            userDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData register(UserData newUser) throws DataAccessException {
        UserData user = this.userDAO.getUser(newUser.username());
        if (user != null) {
            throw new DataAccessException("Already taken");
        }
        this.userDAO.createUser(newUser);
        return authService.createAuth(newUser);
    }

    public AuthData login(UserData user) throws DataAccessException {
        UserData userData = userDAO.getUser(user.username());
        if (userData == null) {
            throw new DataAccessException("User doesn't exist");
        }
        if (!userData.password().equals(user.password())) {
            throw new DataAccessException("Wrong password");
        }
        return this.authService.createAuth(userData);
    }
}
