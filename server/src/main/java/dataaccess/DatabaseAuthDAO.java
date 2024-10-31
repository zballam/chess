package dataaccess;

import model.AuthData;

import static dataaccess.DatabaseManager.createDatabase;

public class DatabaseAuthDAO implements AuthDAO{
    public DatabaseAuthDAO() {
        configureDatabase();
    }

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

    private void configureDatabase() {
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException("ERROR creating database");
        }

    }
}
