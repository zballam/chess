package dataaccess;

import chess.ChessGame;
import model.UserData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    final private HashMap<Integer, GameData> gameDataHashMap = new HashMap<>();
    int nextGameID = 1;

    public void clear() throws DataAccessException {
        this.gameDataHashMap.clear();
    }

    public int createGame(GameData createRequest) throws DataAccessException {
        ChessGame newGame = new ChessGame();
        String whiteUsername = createRequest.whiteUsername();
        String blackUsername = createRequest.blackUsername();
        String gameName = createRequest.gameName();
        GameData newData = new GameData(nextGameID, whiteUsername, blackUsername, gameName, newGame);
        this.gameDataHashMap.put(nextGameID,newData);
        nextGameID++;
        return newData.gameID();
    }

    /**
     * @return Returns GameData if game is found, otherwise returns null
     */
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData gameData : this.gameDataHashMap.values()) {
            if (gameData.gameID() == gameID) {
                return gameData;
            }
        }
        return null;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> gamesList = new ArrayList<>();
        gamesList.addAll(this.gameDataHashMap.values());
        return gamesList;
    }

    public void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {
        GameData game;
        if (playerColor == ChessGame.TeamColor.WHITE) {
            game.whiteUsername() = user.username();
        }
    }
}
