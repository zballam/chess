package dataaccess;

import model.UserData;

import java.sql.SQLException;

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

    private int executeQuery(String statement) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
            return 0;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private final String createStatement =
            """
            CREATE TABLE IF NOT EXISTS  user (
              username varchar(256) NOT NULL,
              password varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              PRIMARY KEY (username)
            )
            """;

    private void configureDatabase() {
        try {
            DatabaseManager.createDatabase();
            executeQuery(createStatement);
        } catch (DataAccessException e) {
            throw new RuntimeException("ERROR configuring database");
        }
    }
}
