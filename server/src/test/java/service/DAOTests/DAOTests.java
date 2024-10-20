package service.DAOTests;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;




// IGNORE THESE TESTS FOR NOW. USE THIS AS TEMPLATES IF NEEDED FOR DAO TEST CLASSES





public class DAOTests {
    static AuthDAO authDAO;
    static GameDAO gameDAO;
    static UserDAO userDAO;

    static AuthData testAuthData;
    static GameData testGameData;
    static UserData testUserData;
//    @Test
//    public void simpleAssertionTest() {
//        assertEquals(200, 100 + 100);
//        assertTrue(100 == 2 * 50);
//        assertNotNull(new Object(), "Response did not return authentication String");
//        assertThrows(InvalidArgumentException.class, () -> {
//            throw new InvalidArgumentException();
//        });
//    }

//    @BeforeAll
//    public static void init() {
//        Boolean memoryAccess = true;
//
//        if (memoryAccess) {
//            authDAO = new MemoryAuthDAO();
//            gameDAO = new MemoryGameDAO();
//            userDAO = new MemoryUserDAO();
//        }
//
//        testAuthData = new AuthData("1234567890", "username");
//        testGameData = new GameData(1,"whiteUsername","blackUsername",
//                            "gameName",new ChessGame());
//        testUserData = new UserData("username","password","email");
//    }
//
//    @BeforeEach
//    public void setup() throws DataAccessException {
//        authDAO.clear();
//        gameDAO.clear();
//        userDAO.clear();
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("AuthDAO create auth")
//    public void createAuthDAO() throws DataAccessException {
//        authDAO.createAuth(testAuthData);
//        assertEquals(testAuthData.authToken(), authDAO.getAuth(testAuthData.authToken()),
//                "Invalid AuthToken returned");
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("AuthDAO retrieve non-existing authToken")
//    public void noExistTokenAuthDAO() throws DataAccessException {
//        assertEquals(null, authDAO.getAuth(testAuthData.authToken()),
//                "Invalid AuthToken returned");
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("AuthDAO clear")
//    public void clearAuthDAO() throws DataAccessException {
//        authDAO.createAuth(testAuthData);
//        authDAO.clear();
//        assertEquals(null, authDAO.getAuth(testAuthData.authToken()),
//                "Clear failed");
//    }
}
