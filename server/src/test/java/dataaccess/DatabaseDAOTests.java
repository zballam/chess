package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        userDAO.createUser(testUser);
        assertEquals(testUser.username(),userDAO.getUser("testUser").username(),"Incorrect Username");
        assertEquals(testUser.email(),userDAO.getUser("testUser").email(),"Incorrect Email");
    }

    @Test
    @DisplayName("Create Duplicate User")
    public void createDuplicateUserTest() throws DataAccessException {
        userDAO.createUser(testUser);
        assertThrows(DataAccessException.class, () -> {userDAO.createUser(testUser);});
    }

    @Test
    @DisplayName("Clear")
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
    @DisplayName("Create Duplicate Auth")
    public void createBadAuthTest() throws DataAccessException {
        authDAO.createAuth(testAuth);
        assertThrows(DataAccessException.class, () -> {authDAO.createAuth(testAuth);});
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

    public void assertGame() throws DataAccessException {
        assertEquals(testGame.gameID(),gameDAO.getGame(testGame.gameID()).gameID(),"Invalid gameID");
        assertEquals(testGame.whiteUsername(),gameDAO.getGame(testGame.gameID()).whiteUsername(),"Invalid whiteUsername");
        assertEquals(testGame.blackUsername(),gameDAO.getGame(testGame.gameID()).blackUsername(),"Invalid blackUsername");
        assertEquals(testGame.gameName(),gameDAO.getGame(testGame.gameID()).gameName(),"Invalid gameName");
    }

    @Test
    @DisplayName("Create Game")
    public void createGameTest() throws DataAccessException {
        gameDAO.createGame(testGame);
        assertGame();
    }

    @Test
    @DisplayName("Create Duplicate Game")
    public void createBadGameTest() throws DataAccessException {
        gameDAO.createGame(testGame);
        assertThrows(DataAccessException.class, () -> {gameDAO.createGame(testGame);});
    }

    @Test
    @DisplayName("Get Game")
    public void getGameTest() throws DataAccessException {
        gameDAO.createGame(testGame);
        assertGame();
    }

    @Test
    @DisplayName("Get Nonexisting Game")
    public void getNoGameTest() throws DataAccessException {
        assertEquals(null,gameDAO.getGame(testGame.gameID()));
    }
}
