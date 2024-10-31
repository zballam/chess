package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        String hashedPW = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        String insertStatement = """
                    INSERT INTO user (username, password, email) VALUES (?, ?, ?);
                    """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement)) {
                ps.setString(1, user.username());
                ps.setString(2, hashedPW);
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
                ps.setString(1,username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String usernameData = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    return new UserData(usernameData, password, email);
                }
                else {
//                    throw new DataAccessException("User doesn't exist");
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
