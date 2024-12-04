package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
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
        nextGameID = 1;
    }

    public int createGame(GameData createRequest) throws DataAccessException {
        for (GameData data : this.gameDataHashMap.values()) {
            if (data.gameName().equals(createRequest.gameName())) {
                throw new DataAccessException("GameName already taken");
            }
        }
        ChessGame newGame = createRequest.game();
        String whiteUsername = createRequest.whiteUsername();
        String blackUsername = createRequest.blackUsername();
        String gameName = createRequest.gameName();
        GameData newData = new GameData(nextGameID, true,  whiteUsername, blackUsername, gameName, newGame);
        this.gameDataHashMap.put(nextGameID,newData);
        nextGameID++;
        return newData.gameID();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData gameData : this.gameDataHashMap.values()) {
            if (gameData.gameID() == gameID) {
                return gameData;
            }
        }
        throw new DataAccessException("GameID doesn't exist");
    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> gamesList = new ArrayList<>();
        gamesList.addAll(this.gameDataHashMap.values());
        return gamesList;
    }

    @Override
    public void updateGame(ChessGame game, int gameID) throws DataAccessException {}

    public void insertUser(int gameID, UserData user, ChessGame.TeamColor playerColor) throws DataAccessException {
        GameData game = this.gameDataHashMap.get(gameID);
        GameData newGame;
        if (game == null) { throw new DataAccessException("Invalid user"); }
        if (playerColor == ChessGame.TeamColor.WHITE) {
            newGame = new GameData(game.gameID(), true, user.username(), game.blackUsername(), game.gameName(), game.game());
        }
        else {
            newGame = new GameData(game.gameID(), true, game.whiteUsername(), user.username(), game.gameName(), game.game());
        }
        this.gameDataHashMap.put(gameID, newGame);
    }

    @Override
    public void endGame(Integer gameID) throws DataAccessException {}
}
