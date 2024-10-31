package dataaccess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class DatabaseDAOTests {

    @BeforeAll
    public static void createDatabase() {
        DatabaseManager.configureDatabase();
    }

    @AfterEach
    public void tearDown() {

    }
}
