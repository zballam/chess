package service.DAOTests;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class authDAOTests {
    static AuthDAO authDAO;

    static AuthData testAuthData;

    @BeforeAll
    public static void init() {
        Boolean memoryAccess = true;

        if (memoryAccess) {
            authDAO = new MemoryAuthDAO();
        }

        testAuthData = new AuthData("1234567890", "username");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO.clear();
    }

    @Test
    @Order(1)
    @DisplayName("create auth")
    public void create() throws DataAccessException {
        authDAO.createAuth(testAuthData);
        assertEquals(testAuthData.authToken(), authDAO.getAuth(testAuthData.authToken()),
                "Invalid AuthToken returned");
    }

    @Test
    @Order(2)
    @DisplayName("get non-existing authToken")
    public void noExistToken() throws DataAccessException {
        assertEquals(null, authDAO.getAuth(testAuthData.authToken()),
                "Invalid AuthToken returned");
    }

    @Test
    @Order(3)
    @DisplayName("clear")
    public void clear() throws DataAccessException {
        authDAO.createAuth(testAuthData);
        authDAO.clear();
        assertEquals(null, authDAO.getAuth(testAuthData.authToken()),
                "Clear failed");
    }

    @Test
    @Order(4)
    @DisplayName("delete auth")
    public void delete() throws DataAccessException {
        authDAO.createAuth(testAuthData);
        authDAO.deleteAuth(testAuthData.authToken());
        assertEquals(null, authDAO.getAuth(testAuthData.authToken()),
                "Delete failed");
    }
}
