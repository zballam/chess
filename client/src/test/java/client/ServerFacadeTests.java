package client;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;
import net.HttpCommunicator;
import net.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;
import ui.Repl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static final Gson GSON = new Gson();
    private static String serverUrl;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl, new Repl(serverUrl));
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void reset() throws IOException {
        URL url = new URL(serverUrl + "/db");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            System.out.println(HttpCommunicator.responseReader(responseBody));
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            System.out.println(HttpCommunicator.responseReader(responseBody));
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

    @Test
    @DisplayName("Logout Test User")
    public void logout() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        assertEquals("{}",facade.logout(tempAuthData.authToken()),"Invalid logout");
    }

    @Test
    @DisplayName("Logout Test User After Login")
    public void logout2() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        facade.logout(tempAuthData.authToken());
        String newJson = facade.login("TestUsername", "TestPassword");
        AuthData authData = GSON.fromJson(newJson, AuthData.class);
        assertEquals("{}",facade.logout(authData.authToken()),"Invalid logout");
    }

    @Test
    @DisplayName("Logout Non-Existing User")
    public void logoutFalse() {
        String noUser = "{ \"message\": \"Error: unauthorized\" }";
        assertEquals(noUser,facade.logout(""));
    }

    @Test
    @DisplayName("Create Game")
    public void createGame() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        String response = facade.createGame("TestGame", tempAuthData.authToken());
        String answer = "{ \"gameID\": ";
        assertEquals(answer,response.substring(0,12));
    }

    @Test
    @DisplayName("Create Already Existing Game")
    public void createGameFalse() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        facade.createGame("TestGame", tempAuthData.authToken());
        String noUser = "{ \"message\": \"Error: GameName already taken\" }";
        assertEquals(noUser,facade.createGame("TestGame", tempAuthData.authToken()));
    }

    @Test
    @DisplayName("List Games")
    public void listGames() {
        Collection<GameData> games = new ArrayList<>();
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        String s = facade.createGame("TestGame1", tempAuthData.authToken());
        var tokens = s.toLowerCase().split(" ");
        int gameID = Integer.parseInt(tokens[2]);
        games.add(new GameData(gameID,null, null,null,"TestGame1",new ChessGame()));
        String s2 = facade.createGame("TestGame2", tempAuthData.authToken());
        var tokens2 = s2.toLowerCase().split(" ");
        int gameID2 = Integer.parseInt(tokens2[2]);
        games.add(new GameData(gameID2,null, null,null,"TestGame2",new ChessGame()));
        String response = facade.listGames(tempAuthData.authToken());
        GamesList testGamesList = new GamesList(games);
        String testGamesListString = GSON.toJson(testGamesList);
        assertEquals(testGamesListString,response);
    }

    @Test
    @DisplayName("Join Game")
    public void joinGameWhite() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        String s = facade.createGame("TestGame", tempAuthData.authToken());
        var tokens = s.toLowerCase().split(" ");
        String result = facade.joinGame(tokens[2],"WHITE", tempAuthData.authToken());
        System.out.println(result);
        assertEquals("{}",result);
    }

    @Test
    @DisplayName("Join Non-Existing Game")
    public void joinGameWhiteFalse() {
        String json = facade.register("TestUsername","TestPassword","TestEmail");
        AuthData tempAuthData = GSON.fromJson(json, AuthData.class);
        String noUser = "{ \"message\": \"Error: bad request\" }";
        assertEquals(noUser,facade.joinGame("2","WHITE", tempAuthData.authToken()));
    }
}
