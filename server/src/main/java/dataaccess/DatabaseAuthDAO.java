package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public class DatabaseAuthDAO implements AuthDAO{
    public DatabaseAuthDAO() {
        DatabaseManager.configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        String clearStatement = "DELETE FROM auth;";
        DatabaseManager.executeUpdate(clearStatement);
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        String insertStatement = """
                    INSERT INTO auth (authToken, username) VALUES (?, ?);
                    """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement)) {
                ps.setString(1, authData.authToken());
                ps.setString(2, authData.username());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }
}
