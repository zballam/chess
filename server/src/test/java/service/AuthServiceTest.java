package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    static AuthService authService;
    static AuthDAO authDAO;
    static UserData testUserData;
    static String testAuthToken;
    static AuthData testAuthData;

    @BeforeAll
    public static void init() {
        authDAO = new MemoryAuthDAO();
        authService = new AuthService(authDAO);
        testUserData = new UserData("username", "password", "email");
        testAuthToken = authService.randGenString(10);
        testAuthData = new AuthData(testAuthToken,testUserData.username());
    }

    @BeforeEach
    public void setup() {
        authService.clear();
    }

    @Test
    @DisplayName("Create")
    public void create() throws DataAccessException {
        assertDoesNotThrow(() -> {authService.createAuth(testUserData,testAuthToken);});
        assertEquals(testAuthData,authDAO.getAuth(testAuthToken));
    }

    @Test
    @DisplayName("Bad create")
    public void badCreate() throws DataAccessException {
        authService.createAuth(testUserData,testAuthToken);
        assertThrows(DataAccessException.class, () -> {authService.createAuth(testUserData,testAuthToken);});
    }

    @Test
    @DisplayName("Clear")
    public void clear() throws DataAccessException {
        authService.createAuth(testUserData,testAuthToken);
        authService.clear();
        assertThrows(DataAccessException.class, () -> {authDAO.getAuth(testAuthToken);});
    }

    @Test
    @DisplayName("Random Number")
    public void randNum() {
        assertNotNull(authService.randGenString(10));
    }

    @Test
    @DisplayName("Delete")
    public void delete() throws DataAccessException {
        authService.createAuth(testUserData, testAuthToken);
        assertDoesNotThrow(() -> {authService.deleteAuth(testAuthData);}, "Delete fail");
    }

    @Test
    @DisplayName("Bad Delete")
    public void badDelete() {
        assertThrows(DataAccessException.class, () -> {authService.deleteAuth(testAuthData);});
    }

    @Test
    @DisplayName("Get")
    public void get() throws DataAccessException {
        authService.createAuth(testUserData, testAuthToken);
        assertDoesNotThrow(() -> {authService.getAuth(testAuthToken);});
    }

    @Test
    @DisplayName("Bad Get")
    public void badGet() {
        assertThrows(DataAccessException.class, () -> {authService.getAuth(testAuthToken);});
    }
}
