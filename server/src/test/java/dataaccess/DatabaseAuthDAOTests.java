package dataaccess;

import org.junit.jupiter.api.BeforeAll;

public class DatabaseAuthDAOTests {

    @BeforeAll
    public static void createDatabase() {
        DatabaseManager.configureDatabase();
    }
}
