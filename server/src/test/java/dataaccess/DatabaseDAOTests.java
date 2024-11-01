package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseDAOTests {
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;
    static UserData testUser;
    static AuthData testAuth;
    static ChessGame game;
    static GameData testGame;

    @BeforeAll
    public static void init() {
        userDAO = new DatabaseUserDAO();
        authDAO = new DatabaseAuthDAO();
        gameDAO = new DatabaseGameDAO();
        testUser = new UserData("testUser","testPassword","testEmail");
        testAuth = new AuthData("testToken", testUser.username());
        game = new ChessGame();
        testGame = new GameData(0,null,null,"testName",game);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    @Test
    @DisplayName("Create User")
    public void createUserTest() throws DataAccessException {
        assertDoesNotThrow(() -> userDAO.createUser(testUser));
    }

    @Test
    @DisplayName("Create Duplicate User")
    public void createDuplicateUserTest() throws DataAccessException {
        userDAO.createUser(testUser);
        assertThrows(DataAccessException.class, () -> {userDAO.createUser(testUser);});
    }

    @Test
    @DisplayName("Create Null User")
    public void createUserNoThrowTest() throws DataAccessException {
        UserData badData = new UserData(null,null, null);
        assertThrows(DataAccessException.class, () -> {userDAO.createUser(badData);});
    }

    @Test
    @DisplayName("Clear User")
    public void clearUserTest() throws DataAccessException {
        userDAO.createUser(testUser);
        userDAO.clear();
        assertEquals(null,userDAO.getUser("testUser"));
    }

    @Test
    @DisplayName("Get User")
    public void getUserTest() throws DataAccessException {
        userDAO.createUser(testUser);
        assertEquals(testUser.username(),userDAO.getUser("testUser").username(),"Incorrect Username");
        assertEquals(testUser.email(),userDAO.getUser("testUser").email(),"Incorrect Email");
    }

    @Test
    @DisplayName("Get Nonexisting User")
    public void getNoUserTest() throws DataAccessException {
        assertEquals(null,userDAO.getUser("testUser"));
    }


    @Test
    @DisplayName("Create Auth")
    public void createAuthTest() throws DataAccessException {
        authDAO.createAuth(testAuth);
        assertEquals(testAuth,authDAO.getAuth(testAuth.authToken()));
    }

    @Test
    @DisplayName("Null Auth Input")
    public void createBadAuthTest() throws DataAccessException {
        AuthData badData = new AuthData(null,null);
        assertThrows(DataAccessException.class, () -> {authDAO.createAuth(badData);});
    }

    @Test
    @DisplayName("Clear Auth")
    public void clearAuthTest() throws DataAccessException {
        authDAO.createAuth(testAuth);
        authDAO.clear();
        assertThrows(DataAccessException.class, () -> {authDAO.getAuth(testAuth.authToken());});
    }

    @Test
    @DisplayName("Clear Auth Multiple")
    public void clearAuthTestMultiple() throws DataAccessException {
        AuthData testAuth2 = new AuthData("test2","test2");
        authDAO.createAuth(testAuth2);
        authDAO.createAuth(testAuth);
        authDAO.clear();
        assertThrows(DataAccessException.class, () -> {authDAO.getAuth(testAuth.authToken());});
    }

    @Test
    @DisplayName("Get Auth")
    public void getAuthTest() throws DataAccessException {
        authDAO.createAuth(testAuth);
        assertEquals(testAuth,authDAO.getAuth(testAuth.authToken()));
    }

    @Test
    @DisplayName("Get Nonexisting Auth")
    public void getNoAuthTest() throws DataAccessException {
        DataAccessException e = assertThrows(DataAccessException.class, () -> {authDAO.getAuth(testAuth.authToken());});
        assertEquals("AuthToken doesn't exist", e.getMessage());
    }

    @Test
    @DisplayName("Delete Auth")
    public void deleteAuthTest() throws DataAccessException {
        authDAO.createAuth(testAuth);
        authDAO.deleteAuth(testAuth.authToken());
        DataAccessException e = assertThrows(DataAccessException.class, () -> {authDAO.getAuth(testAuth.authToken());});
        assertEquals("AuthToken doesn't exist", e.getMessage());
    }

    @Test
    @DisplayName("Delete Nonexisting Auth")
    public void deleteNoAuthTest() throws DataAccessException {
        authDAO.deleteAuth(testAuth.authToken());
        DataAccessException e = assertThrows(DataAccessException.class, () -> {authDAO.getAuth(testAuth.authToken());});
        assertEquals("AuthToken doesn't exist", e.getMessage());
    }

    public void assertGame(int i) throws DataAccessException {
        assertEquals(i,gameDAO.getGame(i).gameID(),"Invalid gameID");
        assertEquals(testGame.whiteUsername(),gameDAO.getGame(i).whiteUsername(),"Invalid whiteUsername");
        assertEquals(testGame.blackUsername(),gameDAO.getGame(i).blackUsername(),"Invalid blackUsername");
        assertEquals(testGame.gameName(),gameDAO.getGame(i).gameName(),"Invalid gameName");
    }

    @Test
    @DisplayName("Create Game")
    public void createGameTest() throws DataAccessException {
        int i = gameDAO.createGame(testGame);
        assertGame(i);
    }

    @Test
    @DisplayName("Create Duplicate Game")
    public void createBadGameTest() throws DataAccessException {
        gameDAO.createGame(testGame);
        assertThrows(DataAccessException.class, () -> {gameDAO.createGame(testGame);});
    }

    @Test
    @DisplayName("Clear Game")
    public void clearGameTest() throws DataAccessException {
        gameDAO.createGame(testGame);
        gameDAO.clear();
        assertEquals(null,gameDAO.getGame(testGame.gameID()));
    }

    @Test
    @DisplayName("Clear Game Multiple")
    public void clearGameTestMultiple() throws DataAccessException {
        GameData testGame2 = new GameData(1,"test2","test2","test2",new ChessGame());
        gameDAO.createGame(testGame2);
        gameDAO.createGame(testGame);
        gameDAO.clear();
        assertEquals(null,gameDAO.getGame(testGame.gameID()));
    }

    @Test
    @DisplayName("Get Game")
    public void getGameTest() throws DataAccessException {
        int i = gameDAO.createGame(testGame);
        assertGame(i);
    }

    @Test
    @DisplayName("Get Nonexisting Game")
    public void getNoGameTest() throws DataAccessException {
        assertEquals(null,gameDAO.getGame(testGame.gameID()));
    }

    public void assertCorrectGame(int i, GameData correctGame) throws DataAccessException {
        assertEquals(i,gameDAO.getGame(i).gameID(),"Invalid gameID");
        assertEquals(correctGame.whiteUsername(),gameDAO.getGame(i).whiteUsername(),"Invalid whiteUsername");
        assertEquals(correctGame.blackUsername(),gameDAO.getGame(i).blackUsername(),"Invalid blackUsername");
        assertEquals(correctGame.gameName(),gameDAO.getGame(i).gameName(),"Invalid gameName");
    }

    @Test
    @DisplayName("Insert White User")
    public void insertWhiteTest() throws DataAccessException {
        int i = gameDAO.createGame(testGame);
        gameDAO.insertUser(i,testUser, ChessGame.TeamColor.WHITE);
        GameData correctGame = new GameData(i, testUser.username(), testGame.blackUsername(), testGame.gameName(), testGame.game());
        assertCorrectGame(i,correctGame);
    }

    @Test
    @DisplayName("Insert Black User")
    public void insertBlackTest() throws DataAccessException {
        int i = gameDAO.createGame(testGame);
        gameDAO.insertUser(i,testUser, ChessGame.TeamColor.BLACK);
        GameData correctGame = new GameData(i, testGame.whiteUsername(), testUser.username(), testGame.gameName(), testGame.game());
        assertCorrectGame(i,correctGame);
    }
}
