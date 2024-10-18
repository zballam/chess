package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {
    public Collection<UserData> getUserDataList();

    // clear Method
    void clear() throws DataAccessException;

    // createUser Method
    void createUser(UserData user) throws DataAccessException;

    // getUser Method
    UserData getUser(String username) throws DataAccessException;
}
