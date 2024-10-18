package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO{
    Collection<AuthData> authDataList = new ArrayList<>();

    public void clear() throws DataAccessException {
//        throw new RuntimeException("Not implemented: MemoryAuthDAO clear");
        this.authDataList.clear();
    }

    public void createAuth() throws DataAccessException {
        throw new RuntimeException("Not implemented: MemoryAuthDAO createAuth");
    }

    public String getAuth() throws DataAccessException {
        throw new RuntimeException("Not implemented: MemoryAuthDAO getAuth");
    }

    public void deleteAuth() throws DataAccessException {
        throw new RuntimeException("Not implemented: MemoryAuthDAO deleteAuth");
    }
}
