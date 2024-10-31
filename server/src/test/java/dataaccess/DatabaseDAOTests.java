package dataaccess;

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

    @BeforeAll
    public static void init() {
        userDAO = new DatabaseUserDAO();
        authDAO = new DatabaseAuthDAO();
        gameDAO = new DatabaseGameDAO();
        testUser = new UserData("testUser","testPassword","testEmail");
        testAuth = new AuthData("testToken", testUser.username());
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
//        DataAccessException e = assertThrows(DataAccessException.class, () -> {userDAO.getUser("testUser");});
//        assertEquals("User not found", e.getMessage());
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
}
