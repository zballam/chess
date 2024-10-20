package server;

import dataaccess.DataAccessException;
import dataaccess.*;
import model.*;
import org.eclipse.jetty.client.HttpResponseException;
import spark.*;
import service.*;
import com.google.gson.*;

import java.util.Objects;

public class Server {
    private static final Gson gson = new Gson();
    private final AuthService authService;
    private final GameService gameService;
    private final UserService userService;

    public Server() {
        // Change these MemoryDAOs to change which interface is used
        this.authService = new AuthService(new MemoryAuthDAO());
        this.gameService = new GameService(new MemoryGameDAO());
        this.userService = new UserService(new MemoryUserDAO(), authService);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    /**
     * Clears the database. Removes all users, games, and authTokens.
     * @return JSON String
     */
    private Object clear(Request req, Response res) throws HttpResponseException {
        try {
            authService.clear();
            gameService.clear();
            userService.clear();
            // Success response: [200] {}
            res.status(200);
            return "{}";
        } catch (Exception e) {
            // Failure response: [500] { "message": "Error: (description of error)" }
            res.status(500);
            return "{ \"message\": \"Error: " + e.getMessage() + "\" }";
        }
    }

    /**
     * Register a new user.
     * @return JSON String
     */
    private Object register(Request req, Response res) { //Throws ResponseException?
        UserData newUser = gson.fromJson(req.body(), UserData.class);
        // Body: { "username":"", "password":"", "email":"" }
        if (newUser.username() == null || newUser.password() == null || newUser.email() == null) {
            // Failure response: [400] { "message": "Error: bad request" }
            res.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        }
        try {
            AuthData registerRes = userService.register(newUser);
            // Success response: [200] { "username":"", "authToken":"" }
            res.status(200);
            return gson.toJson(registerRes);
        } catch (DataAccessException e) {
            if (e.getMessage() == "Already taken") {
                // Failure response: [403] { "message": "Error: already taken" }
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            else {
                // Failure response: [500] { "message": "Error: (description of error)" }
                res.status(500);
                return "{ \"message\": \"Error: " + e.getMessage() + "\" }";
            }
        }
    }

    /**
     * Logs in an existing user (returns a new authToken).
     * @return JSON String
     */
    private Object login(Request req, Response res) { //Throws ResponseException?
        // Body: { "username":"", "password":"" }
        UserData user = gson.fromJson(req.body(), UserData.class);
        try {
            AuthData loginRes = userService.login(user);
            // Success response: [200] { "username":"", "authToken":"" }
            res.status(200);
            return gson.toJson(loginRes);
        } catch (DataAccessException e) {
            if (Objects.equals(e.getMessage(), "User doesn't exist") || Objects.equals(e.getMessage(), "Wrong password")) {
                // Failure response: [401] { "message": "Error: unauthorized" }
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            else {
                // Failure response: [500] { "message": "Error: (description of error)" }
                res.status(500);
                return "{ \"message\": \"Error: " + e.getMessage() + "\" }";
            }
        }
    }

    /**
     * Logs out the user represented by the authToken.
     * @return JSON String
     */
    private Object logout(Request req, Response res) { //Throws ResponseException?
        // Headers: authorization: <authToken>
        AuthData authData = new AuthData(req.headers("Authorization"),null);
        try {
            authService.logout(authData);
            // Success response: [200] {}
            res.status(200);
            return "{}";
        } catch (DataAccessException e) {
            if (Objects.equals(e.getMessage(), "AuthToken doesn't exist")) {
                // Failure response: [401] { "message": "Error: unauthorized" }
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            else {
                // Failure response: [500] { "message": "Error: (description of error)" }
                res.status(500);
                return "{ \"message\": \"Error: " + e.getMessage() + "\" }";
            }
        }
    }

    /**
     * Gives a list of all games.
     * @return JSON String
     */
    // Note that whiteUsername and blackUsername may be null.
    private Object listGames(Request req, Response res) { //Throws ResponseException?
        // Headers: authorization: <authToken>
        // Success response: [200] { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
        // Failure response: [401] { "message": "Error: unauthorized" }
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Creates a new game.
     * @return JSON String
     */
    // Note that whiteUsername and blackUsername may be null.
    private Object createGame(Request req, Response res) { //Throws ResponseException?
        // Headers: authorization: <authToken>
        // Body: { "gameName":"" }
        // Success response: [200] { "gameID": 1234 }
        // Failure response: [400] { "message": "Error: bad request" }
        // Failure response: [401] { "message": "Error: unauthorized" }
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Verifies that the specified game exists and adds the caller as the requested color to the game.
     * @return JSON String
     */
    // Note that whiteUsername and blackUsername may be null.
    private Object joinGame(Request req, Response res) { //Throws ResponseException?
        // Headers: authorization: <authToken>
        // Body: { "playerColor":"WHITE/BLACK", "gameID": 1234 }
        // Success response: [200] {}
        // Failure response: [400] { "message": "Error: bad request" }
        // Failure response: [401] { "message": "Error: unauthorized" }
        // Failure response: [403] { "message": "Error: already taken" }
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }
}
