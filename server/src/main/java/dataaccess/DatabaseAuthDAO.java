package dataaccess;

import model.AuthData;

public class DatabaseAuthDAO implements AuthDAO{
    public DatabaseAuthDAO() {
        DatabaseManager.configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        String clearStatement = "DELETE FROM auth;";
        DatabaseManager.executeUpdate(clearStatement, true);
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
