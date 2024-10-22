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
        return authService.createAuth(newUser,authService.randGenString(10));
    }

    public AuthData login(UserData user) throws DataAccessException {
        UserData userData = userDAO.getUser(user.username());
        if (userData == null) {
            throw new DataAccessException("User doesn't exist");
        }
        if (!userData.password().equals(user.password())) {
            throw new DataAccessException("Wrong password");
        }
        return this.authService.createAuth(userData, authService.randGenString(10));
    }

    public void logout(AuthData auth) throws DataAccessException {
        authService.getAuth(auth.authToken());
        AuthData authData = authService.getAuth(auth.authToken());
        if (authData == null) {
            throw new DataAccessException("No AuthData");
        }
        authService.deleteAuth(authData);
    }
}
