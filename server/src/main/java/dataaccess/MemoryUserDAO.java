package dataaccess;

import model.UserData;

public class MemoryUserDAO implements UserDAO{
    public void clear() throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }

    public void createUser(UserData user) throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }

    public UserData getUser(String username) throws DataAccessException {
        throw new RuntimeException("Not implemented");
    }
}
