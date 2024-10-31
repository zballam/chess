package dataaccess;

import model.UserData;

public class DatabaseUserDAO implements UserDAO{
    private final String createStatement =
            """
            CREATE TABLE IF NOT EXISTS  user (
              username varchar(256) NOT NULL,
              password varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              PRIMARY KEY (username)
            )
            """;

    public DatabaseUserDAO() {
        DatabaseManager.configureDatabase(createStatement);
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
}
