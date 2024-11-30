package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


public class DatabaseGameDAO implements GameDAO{
    private static final Gson GSON = new Gson();

    public DatabaseGameDAO() {
        DatabaseManager.configureDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        String clearStatement = "DELETE FROM game;";
        DatabaseManager.executeUpdate(clearStatement);
    }

    public GameData getGameName(String gameName) throws DataAccessException {
        String userQuery = """
                SELECT * FROM game WHERE gameName = ?;
                """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(userQuery)) {
                ps.setString(1, String.valueOf(gameName));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return readRS(rs);
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
    public int createGame(GameData createRequest) throws DataAccessException {
        if (getGameName(createRequest.gameName()) != null) {
            throw new DataAccessException("GameName already taken");
        }
        // Serialize game
//        String gameInfo = GSON.toJson(createRequest.game());
        String gameInfo = GSON.toJson(new ChessGame()); // Actually create a game this time
        String insertStatement = """
                    INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?);
                    """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement,Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, createRequest.whiteUsername());
                ps.setString(2, createRequest.blackUsername());
                ps.setString(3, createRequest.gameName());
                ps.setString(4, gameInfo);
                ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private GameData readRS(ResultSet rs) throws SQLException {
        int dataGameID = rs.getInt(1);
        String whiteUsername = rs.getString(2);
        String blackUsername = rs.getString(3);
        String gameName = rs.getString(4);
        String game = rs.getString(5);
        ChessGame gameData = GSON.fromJson(game, ChessGame.class);
        return new GameData(dataGameID, whiteUsername, blackUsername, gameName, gameData);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        String userQuery = """
                SELECT * FROM game WHERE gameID = ?;
                """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(userQuery)) {
                ps.setString(1, String.valueOf(gameID));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return readRS(rs);
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
    public Collection<GameData> listGames() throws DataAccessException {
        String gameQuery = """
                SELECT * FROM game;
                """;
        Collection<GameData> data = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(gameQuery)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    data.add(readRS(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return data;
    }

    @Override
    public void updateGame(ChessGame game, int gameID) throws DataAccessException { // Needs to be tested
        String updateStatement = """
                UPDATE game SET game = ? WHERE gameID=?
                """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(updateStatement)) {
                ps.setString(1, new Gson().toJson(game));
                ps.setString(2, String.valueOf(gameID));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {
        String insertStatement;
        if (playerColor == ChessGame.TeamColor.WHITE) {
            insertStatement = """
                    UPDATE game SET whiteUsername = ? WHERE gameID=?;
                    """;
        }
        else {
            insertStatement = """
                    UPDATE game SET blackUsername = ? WHERE gameID=?;
                    """;
        }
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement)) {
                ps.setString(1, user.username());
                ps.setString(2, String.valueOf(gameID));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
