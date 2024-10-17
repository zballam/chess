package service;

import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.*;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void clear() {
        //userDAO.clear();
    }

//    public AuthData register(UserData user) {
//        throw new RuntimeException("Not implemented");
//    }
//
//    public AuthData login(UserData user) {
//        throw new RuntimeException("Not implemented");
//    }

    public void logout(AuthData auth) {
        throw new RuntimeException("Not implemented");
    }
}
