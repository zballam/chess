package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.ResultSet;
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
        String userQuery = """
                SELECT * FROM auth WHERE authToken = ?;
                """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(userQuery)) {
                ps.setString(1,authToken);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String authTokenData = rs.getString(1);
                    String username = rs.getString(2);
                    return new AuthData(authTokenData,username);
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        String insertStatement = """
                    DELETE FROM auth WHERE authToken=?;
                    """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement)) {
                ps.setString(1, authToken);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
