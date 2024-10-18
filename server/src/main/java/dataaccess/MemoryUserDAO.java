package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
    Collection<UserData> userDataList = new ArrayList<>();

    public void clear() throws DataAccessException {
//        throw new RuntimeException("Not implemented: MemoryUserDAO clear");
        this.userDataList.clear();
    }

    public void createUser(UserData user) throws DataAccessException {
        throw new RuntimeException("Not implemented: MemoryUserDAO createUser");
    }

    public UserData getUser(String username) throws DataAccessException {
        throw new RuntimeException("Not implemented: MemoryUserDAO getUser");
    }
}
