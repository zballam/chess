package dataaccess;

import org.junit.jupiter.api.*;

public class DatabaseDAOTests {
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;

    @BeforeAll
    public static void init() {
        userDAO = new DatabaseUserDAO();
        authDAO = new DatabaseAuthDAO();
        gameDAO = new DatabaseGameDAO();
    }

    @BeforeEach
    public void addData() throws DataAccessException {
        String userStatement = """
                        INSERT INTO user (username, password, email) VALUES 
                        (\'usernameTest\', \'passwordTest\', \'emailTest\');
                        """;
        DatabaseManager.executeUpdate(userStatement);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }

    @Test
    @DisplayName("Clear User")
    public void clearUserTest() throws DataAccessException {
        userDAO.clear();

    }
}
