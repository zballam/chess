package dataaccess;
import chess.ChessGame;
import model.UserData;
import service.GameService;
import java.util.Collection;

public interface GameDAO {
    // clear Method
    void clear() throws DataAccessException;

    // createGame Method
//    void createGame(GameService createRequest, String authToken) throws DataAccessException;

    // getGame Method
    ChessGame getGame(int gameID) throws DataAccessException;

    // listGames Method
    Collection<ChessGame> listGames() throws DataAccessException;

    // updateGame Method // Probably just the addUser Method

    // addUser Method
    void insertUser(UserData user, ChessGame.TeamColor playerColor) throws DataAccessException;
}
