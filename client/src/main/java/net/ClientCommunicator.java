package net;

import netscape.javascript.JSObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientCommunicator {
    private final String serverUrl;

    public ClientCommunicator(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private HttpURLConnection connect(String urlString, String requestMethod) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod(requestMethod);
        return connection;
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

    // Use this to actually make GET POST and other HTTP requests
}
