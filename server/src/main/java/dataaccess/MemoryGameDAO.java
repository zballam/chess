package dataaccess;

import chess.ChessGame;
import model.UserData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    Collection<GameData> gameDataList = new ArrayList<>();
    int nextGameID = 1;
    // Might need to initialize these in the constructor instead

    public void clear() throws DataAccessException {
        this.gameDataList.clear();
    }

    public int createGame(GameData createRequest) throws DataAccessException {
        ChessGame newGame = new ChessGame();
        String whiteUsername = createRequest.whiteUsername();
        String blackUsername = createRequest.blackUsername();
        String gameName = createRequest.gameName();
        GameData newData = new GameData(nextGameID, whiteUsername, blackUsername, gameName, newGame);
        this.gameDataList.add(newData);
        return newData.gameID();
    }

    /**
     * @return Returns GameData if game is found, otherwise returns null
     */
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData gameData : this.gameDataList) {
            if (gameData.gameID() == gameID) {
                return gameData;
            }
        }
        return null;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> gamesList = new ArrayList<>();
        boolean b = gamesList.addAll(this.gameDataList);
        return gamesList;
    }

    public void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {

    }
}
