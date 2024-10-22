package DAO;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.GameDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryGameDAO;
import model.GameData;
import model.GamesList;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameDAOTests {
    static GameDAO gameDAO;

    static GameData testGameData;

    static Gson gson;

    @BeforeAll
    public static void init() {
        gameDAO = new MemoryGameDAO();

        testGameData = new GameData(1,"whiteUsername","blackUsername",
                            "gameName",new ChessGame());

        gson = new Gson();
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO.clear();
    }

//    @Test
//    @Order(1)
//    @DisplayName("create game")
//    public void create() throws DataAccessException {
//        gameDAO.createGame(testGameData);
//        assertEquals(testGameData, gameDAO.getGame(testGameData.gameID()),
//                "Invalid gameData returned");
//    }

    @Test
    @Order(2)
    @DisplayName("clear")
    public void clear() throws DataAccessException {
        gameDAO.createGame(testGameData);
        gameDAO.clear();
        assertEquals(null, gameDAO.getGame(testGameData.gameID()),
                "Clear failed");
    }

//    @Test
//    @Order(3)
//    @DisplayName("list games")
//    public void listGames() throws DataAccessException {
//        GameData testGameData2 = new GameData(2,"whiteUsername2","blackUsername2",
//                "gameNam2",new ChessGame());
//        GameData testGameData3 = new GameData(3,"whiteUsername3","blackUsername3",
//                "gameName3",new ChessGame());
//        Collection<GameData> gamesList = new ArrayList<>();
//        gamesList.add(testGameData);
//        gamesList.add(testGameData2);
//        gamesList.add(testGameData3);
//
//        gameDAO.createGame(testGameData);
//        gameDAO.createGame(testGameData2);
//        gameDAO.createGame(testGameData3);
//        gameDAO.clear();
//        new GamesList(gamesList);
//        assertEquals(gson.toJson(new GamesList(gamesList)), gson.toJson(new GamesList(gameDAO.listGames())),
//                "Invalid games listed");
//    }
}
