package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.List;

public class DatabaseGameDAO implements GameDAO{
    private final String createStatement =
            """
            CREATE TABLE IF NOT EXISTS  game (
              gameID int NOT NULL,
              whiteUsername varchar(256) NULL,
              blackUsername varchar(256) NULL,
              gameName varchar(256) NOT NULL,
              game varchar(512) NULL,
              FOREIGN KEY(whiteUsername) REFERENCES user(username),
              FOREIGN KEY(blackUsername) REFERENCES user(username)
            )
            """;

    public DatabaseGameDAO() {
        DatabaseManager.configureDatabase(createStatement);
    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public int createGame(GameData createRequest) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {

    }
}
