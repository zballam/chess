package net;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpCommunicator {
    private final String serverUrl;

    public HttpCommunicator(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private HttpURLConnection connect(String urlString, String requestMethod) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod(requestMethod);
        return connection;
    }

    public static String responseReader(InputStream responseBody) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private String readResponseCode(HttpURLConnection con) throws IOException {
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = con.getInputStream();
            return responseReader(responseBody);
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = con.getErrorStream();
            return responseReader(responseBody);
        }
    }

    public String register(String json) throws IOException {
        String registerUrl = serverUrl + "/user";
        HttpURLConnection con = connect(registerUrl, "POST");
        con.setDoOutput(true);
        con.connect();
        try(OutputStream requestBody = con.getOutputStream();) {
            requestBody.write(json.getBytes(StandardCharsets.UTF_8));
        }
        return readResponseCode(con);
    }

    public String login(String json) throws IOException {
        String loginUrl = serverUrl + "/session";
        HttpURLConnection con = connect(loginUrl, "POST");
        con.setDoOutput(true);
        con.connect();
        try(OutputStream requestBody = con.getOutputStream();) {
            requestBody.write(json.getBytes(StandardCharsets.UTF_8));
        }
        return readResponseCode(con);
    }

    public String logout(String authToken) throws IOException {
        String logoutUrl = serverUrl + "/session";
        HttpURLConnection con = connect(logoutUrl, "DELETE");
        con.addRequestProperty("Authorization", authToken);
        con.connect();
        return readResponseCode(con);
    }

    public String createGame(String json, String authToken) throws IOException {
        String createGameUrl = serverUrl + "/game";
        HttpURLConnection con = connect(createGameUrl, "POST");
        con.addRequestProperty("Authorization", authToken);
        con.setDoOutput(true);
        con.connect();
        try(OutputStream requestBody = con.getOutputStream();) {
            requestBody.write(json.getBytes(StandardCharsets.UTF_8));
        }
        return readResponseCode(con);
    }

    public String listGames(String authToken) throws IOException {
        String listGamesUrl = serverUrl + "/game";
        HttpURLConnection con = connect(listGamesUrl, "GET");
        con.addRequestProperty("Authorization", authToken);
        con.connect();
        return readResponseCode(con);
    }

    public String joinGame(String json, String authToken) throws IOException {
        String joinGameUrl = serverUrl + "/game";
        HttpURLConnection con = connect(joinGameUrl, "PUT");
        con.addRequestProperty("Authorization", authToken);
        con.setDoOutput(true);
        con.connect();
        try(OutputStream requestBody = con.getOutputStream();) {
            requestBody.write(json.getBytes(StandardCharsets.UTF_8));
        }
        return readResponseCode(con);
    }
}
