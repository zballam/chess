package dataaccess;

import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
        String insertStatement = """
                    INSERT INTO user (username, password, email) VALUES (?, ?, ?);
                    """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement)) {
                ps.setString(1, user.username());
                ps.setString(2, user.password());
                ps.setString(3, user.email());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String userQuery = """
                SELECT * FROM user WHERE username = ?;
                """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(userQuery)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String usernameData = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    return new UserData(usernameData,password,email);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }
}
