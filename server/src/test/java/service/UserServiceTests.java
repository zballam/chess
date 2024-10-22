package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceTests {
    static AuthDAO authDAO;
    static UserDAO userDAO;
    static AuthService authService;
    static UserService userService;

    static UserData testUserData;

    @BeforeAll
    public static void init() {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        authService = new AuthService(authDAO);
        userService = new UserService(userDAO, authService);

        testUserData = new UserData("username", "password", "email");
    }

    @BeforeEach
    public void setup() {
        userService.clear();
    }

    @Test
    @DisplayName("Register")
    public void reg() throws DataAccessException {
        userService.register(testUserData);
        assertEquals(testUserData,userDAO.getUser(testUserData.username()));
    }

    @Test
    @DisplayName("Bad Register")
    public void badReg() throws DataAccessException {
        userService.register(testUserData);
        assertThrows(DataAccessException.class, () -> userService.register(testUserData));
    }

    @Test
    @DisplayName("Login")
    public void log() throws DataAccessException {
        userService.register(testUserData);
        AuthData auth = userService.login(testUserData);
        assertEquals(auth,authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("User Doesn't Exist Login")
    public void noUserLog() {
        assertThrows(DataAccessException.class, () -> userService.login(testUserData));
    }

    @Test
    @DisplayName("Wrong Password")
    public void wrongPassword() throws DataAccessException {
        userService.register(testUserData);
        UserData wrongPasswordUser = new UserData("username", "wrongPassword", "email");
        assertThrows(DataAccessException.class, () -> userService.login(wrongPasswordUser));
    }

    @Test
    @DisplayName("Logout")
    public void outLog() throws DataAccessException {
        AuthData auth = userService.register(testUserData);
        userService.logout(auth);
        assertThrows(DataAccessException.class, () -> authDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("User Doesn't Exist Logout")
    public void noUserOut() {
        assertThrows(DataAccessException.class, () -> userService.logout(new AuthData("0","0")));
    }
}
