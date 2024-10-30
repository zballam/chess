package dataaccess;

import model.AuthData;

public class DatabaseAuthDAO implements AuthDAO{

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }
}
