package dataaccess;

import model.UserData;

public class DatabaseUserDAO implements UserDAO{
    public DatabaseUserDAO() {
        DatabaseManager.configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        String clearStatement = "DELETE FROM user;";
        DatabaseManager.executeUpdate(clearStatement);
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }
}
