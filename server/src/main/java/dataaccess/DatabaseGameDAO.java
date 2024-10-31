package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


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

    @Override
    public int createGame(GameData createRequest) throws DataAccessException {
        // Serialize game
        String gameInfo = GSON.toJson(createRequest.game());
        String insertStatement = """
                    INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?);
                    """;
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(insertStatement)) {
                ps.setInt(1, createRequest.gameID());
                ps.setString(2, createRequest.whiteUsername());
                ps.setString(3, createRequest.blackUsername());
                ps.setString(4, createRequest.gameName());
                ps.setString(5, gameInfo);
                ps.executeUpdate();
                return createRequest.gameID();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
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
                    int dataGameID = rs.getInt(1);
                    String whiteUsername = rs.getString(2);
                    String blackUsername = rs.getString(3);
                    String gameName = rs.getString(4);
                    String game = rs.getString(5);
                    ChessGame gameData = GSON.fromJson(game, ChessGame.class);
                    return new GameData(dataGameID, whiteUsername, blackUsername, gameName, gameData);
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

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {

    }
}
