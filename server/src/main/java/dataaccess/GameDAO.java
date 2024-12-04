package dataaccess;
import chess.ChessGame;
import chess.ChessMove;
import model.GameData;
import model.UserData;
import java.util.Collection;

public interface GameDAO {
    // clear Method
    void clear() throws DataAccessException;

    // createGame Method
    int createGame(GameData createRequest) throws DataAccessException;

    // getGame Method
    GameData getGame(int gameID) throws DataAccessException;

    // listGames Method
    Collection<GameData> listGames() throws DataAccessException;

    // updateGame Method
    void updateGame(ChessGame game, int gameID) throws DataAccessException;

    // addUser Method
    void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException;

    void endGame(Integer gameID) throws DataAccessException;
}
