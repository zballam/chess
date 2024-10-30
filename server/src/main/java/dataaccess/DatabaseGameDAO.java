package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.List;

public class DatabaseGameDAO implements GameDAO{
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
