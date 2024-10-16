package server;

import spark.*;
import service.*;

public class Server {

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
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    /**
     * Clears the database. Removes all users, games, and authTokens.
     * @return JSON Object
     */
    private Object clear(Request req, Response res) { //Throws ResponseException?
        // Is this the handler?
        // Does this just deserialize and serialize the data?
        ClearHandler handler = new ClearHandler();
        // Success response: [200] {}
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Register a new user.
     * @return JSON Object
     */
    private Object register(Request req, Response res) { //Throws ResponseException?
        // Body: { "username":"", "password":"", "email":"" }
        // Success response: [200] { "username":"", "authToken":"" }
        // Failure response: [400] { "message": "Error: bad request" }
        // Failure response: [403] { "message": "Error: already taken" }
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Logs in an existing user (returns a new authToken).
     * @return JSON Object
     */
    private Object login(Request req, Response res) { //Throws ResponseException?
        // Body: { "username":"", "password":"" }
        // Success response: [200] { "username":"", "authToken":"" }
        // Failure response: [401] { "message": "Error: unauthorized" }
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Logs out the user represented by the authToken.
     * @return JSON Object
     */
    private Object logout(Request req, Response res) { //Throws ResponseException?
        // Headers: authorization: <authToken>
        // Success response: [200] {}
        // Failure response: [401] { "message": "Error: unauthorized" }
        // Failure response: [500] { "message": "Error: (description of error)" }
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gives a list of all games.
     * @return JSON Object
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
     * @return JSON Object
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
     * @return JSON Object
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
