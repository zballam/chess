package dataaccess;

import model.UserData;

public interface UserDAO {
    // clear Method
    void clear() throws DataAccessException;

    // createUser Method
    void createUser(UserData user) throws DataAccessException;

    // getUser Method
    UserData getUser(String username) throws DataAccessException;
}
