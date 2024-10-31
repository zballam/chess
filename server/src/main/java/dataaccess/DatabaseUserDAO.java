package dataaccess;

import model.UserData;

public class DatabaseUserDAO implements UserDAO{
    public DatabaseUserDAO() {
        configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    private void configureDatabase() {
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException("ERROR creating database");
        }

    }
}
