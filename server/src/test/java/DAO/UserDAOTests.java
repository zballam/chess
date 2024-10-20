package DAO;

import dataaccess.UserDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDAOTests {
    static UserDAO userDAO;

    static UserData testUserData;

    @BeforeAll
    public static void init() {
        userDAO = new MemoryUserDAO();

        testUserData = new UserData("username","password","email");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        userDAO.clear();
    }

    @Test
    @Order(1)
    @DisplayName("create user")
    public void create() throws DataAccessException {
        userDAO.createUser(testUserData);
        assertEquals(testUserData, userDAO.getUser(testUserData.username()),
                "Invalid UserData returned");
    }

    @Test
    @Order(2)
    @DisplayName("clear")
    public void clear() throws DataAccessException {
        userDAO.createUser(testUserData);
        userDAO.clear();
        assertEquals(null, userDAO.getUser(testUserData.username()),
                "Clear failed");
    }
}
