package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    static AuthDAO authDAO;
    static UserDAO userDAO;
    static GameDAO gameDAO;
    static AuthService authService;
    static UserService userService;
    static GameService gameService;

    static String testAuthToken;
    static AuthData testAuthData;
    static AuthData badAuthData;
    static UserData testUserData;
    static GameData testGameData;

    @BeforeAll
    public static void init() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();
        authService = new AuthService(authDAO);
        userService = new UserService(userDAO, authService);
        gameService = new GameService(gameDAO, authService, userService);

        testAuthToken = authService.randGenString(10);
        testAuthData = new AuthData(testAuthToken, "username");
        badAuthData = new AuthData("0000000000", "username");
        testUserData = new UserData("username", "password", "email");
        testGameData = new GameData(1, null,null, null,
                "gameName", new ChessGame());

        authService.createAuth(testUserData,testAuthToken);
        userService.register(testUserData);
    }

    @BeforeEach
    public void setup() {
        gameService.clear();
    }

    @Test
    @DisplayName("Create")
    public void create() throws DataAccessException {
        assertDoesNotThrow(() -> {gameService.createGame(testGameData,testAuthData);});
        assertEquals(testGameData,gameDAO.getGame(testGameData.gameID()));
    }

    @Test
    @DisplayName("Bad create")
    public void badCreate() throws DataAccessException {
        gameService.createGame(testGameData,testAuthData);
        assertThrows(DataAccessException.class, () -> {gameService.createGame(testGameData,testAuthData);});
    }

    @Test
    @DisplayName("Clear")
    public void clear() throws DataAccessException {
        gameService.createGame(testGameData,testAuthData);
        gameService.clear();
        assertThrows(DataAccessException.class, () -> {gameDAO.getGame(testGameData.gameID());});
    }

    @Test
    @DisplayName("List Games")
    public void listGames() throws DataAccessException {
        Collection<GameData> gamesList = new ArrayList<>();
        int iD = gameService.createGame(testGameData,testAuthData);
        gamesList.add(gameDAO.getGame(iD));
        assertEquals(gamesList,gameService.listGames(testAuthData), "Wrong gameData returned");
    }

    @Test
    @DisplayName("Bad Authentication List Games")
    public void badAuthListGames() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {gameService.listGames(badAuthData);});
    }

    @Test
    @DisplayName("Join Game White")
    public void joinGameWhite() throws DataAccessException {
        gameService.createGame(testGameData,testAuthData);
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, 1);
        gameService.joinGame(joinGameRequest,testAuthData);
        System.out.println();
        assertEquals("username",gameDAO.getGame(1).whiteUsername(), "Wrong whiteUsername returned");
    }

    @Test
    @DisplayName("Join Game Black")
    public void joinGameBlack() throws DataAccessException {
        gameService.createGame(testGameData,testAuthData);
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.BLACK, 1);
        gameService.joinGame(joinGameRequest,testAuthData);
        System.out.println();
        assertEquals("username",gameDAO.getGame(1).blackUsername(), "Wrong blackUsername returned");
    }

    @Test
    @DisplayName("Join Game Bad Request")
    public void joinGameBadRequest() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.BLACK, 1);
        assertThrows(DataAccessException.class, () -> {gameService.joinGame(joinGameRequest,testAuthData);});
    }
}
