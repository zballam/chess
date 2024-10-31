package dataaccess;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) {
                    throw new Exception("Unable to load db.properties");
                }
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Execute a SQL Update query
     *
     * @param statement The SQL statement to execute
     */
    public static void executeUpdate(String statement) throws DataAccessException {
        try (var conn = getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Used to execute a query on the User table
     *
     * @param statement SQL query statement to execute
     * @return Returns a collection of UserData
     */
    public static Collection<UserData> executeUserQuery(String statement) throws DataAccessException {
        Collection<UserData> data = new ArrayList<>();
        try (var conn = getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    data.add(new UserData(username,password,email));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return data;
    }

    private static final String createUserTableStatement =
            """
            CREATE TABLE IF NOT EXISTS user (
              username varchar(256) NOT NULL,
              password varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              PRIMARY KEY (username)
            )
            """;

    private static final String createGameTableStatement =
            """
            CREATE TABLE IF NOT EXISTS game (
              gameID int NOT NULL,
              whiteUsername varchar(256) NULL,
              blackUsername varchar(256) NULL,
              gameName varchar(256) NOT NULL,
              game varchar(512) NULL
            )
            """;
            //FOREIGN KEY(whiteUsername) REFERENCES user(username),
            //FOREIGN KEY(blackUsername) REFERENCES user(username)

    private static final String createAuthTableStatement =
            """
            CREATE TABLE IF NOT EXISTS auth (
              authToken varchar(256) NOT NULL,
              username varchar(256) NOT NULL,
              PRIMARY KEY(username)
            )
            """;
            //FOREIGN KEY(username) REFERENCES user(username)

    public static void configureDatabase() {
        try {
            createDatabase();
            executeUpdate(createUserTableStatement);
            executeUpdate(createGameTableStatement);
            executeUpdate(createAuthTableStatement);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
