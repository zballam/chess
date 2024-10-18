package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.*;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void clear() {
        try {
            userDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(UserData newUser) throws DataAccessException {
        UserData user = this.userDAO.getUser(newUser.username());
        if (user != null) {
            throw new DataAccessException("Already taken");
        }
        this.userDAO.createUser(newUser);
    }

    public AuthData login(UserData user) throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }

    public void logout(AuthData auth) throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }
}
