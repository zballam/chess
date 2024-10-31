package dataaccess;

import model.AuthData;

public class DatabaseAuthDAO implements AuthDAO{
    private final String createStatement =
            """
            CREATE TABLE IF NOT EXISTS auth (
              authToken varchar(256) NOT NULL,
              username varchar(256) NULL,
              FOREIGN KEY(username) REFERENCES user(username)
            )
            """;

    public DatabaseAuthDAO() {
        DatabaseManager.configureDatabase(createStatement);
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
}
