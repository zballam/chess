package client;

import com.google.gson.Gson;
import model.*;
import net.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static final Gson GSON = new Gson();

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    private String responseReader(InputStream responseBody) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    @BeforeEach
    public void reset() throws IOException {
        URL url = new URL("http://localhost:8080/db");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            System.out.println(responseReader(responseBody));
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            System.out.println(responseReader(responseBody));
        }
    }

    @Test
    @DisplayName("Register New User")
    public void register() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData authData = GSON.fromJson(json, AuthData.class);
        assertEquals("TestUsername",authData.username(),"Invalid username returned");
        assertNotNull(authData.authToken(), "No authToken");
    }

    @Test
    @DisplayName("Register Existing User")
    public void registerFalse() {
        facade.register("TestUsername","TestPassword","TestEmail");
        String alreadyTaken = "{ \"message\": \"Error: already taken\" }";
        assertEquals(alreadyTaken,facade.register("TestUsername","",""));
    }

    @Test
    @DisplayName("Login Test User")
    public void login() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        facade.logout(tempAuthData.authToken());
        String newJson = facade.login("TestUsername", "TestPassword");
        AuthData authData = GSON.fromJson(newJson, AuthData.class);
        assertEquals("TestUsername",authData.username(),"Invalid username returned");
        assertNotNull(authData.authToken(), "No authToken");
    }

    @Test
    @DisplayName("Login Non-Existing User")
    public void loginFalse() {
        String noUser = "{ \"message\": \"Error: unauthorized\" }";
        assertEquals(noUser,facade.login("TestUsername","TestPassword"));
    }
}
