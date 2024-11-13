package net;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }



    // Create URL object
    // url.openConnection();
    // setReadTimeout(5000);
    // connection.connect();
        // will wait until server responds
    // if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
    // .getHeaderField()
    // InputStream responseBody = conection.getInputStream();
    // use connection.getErrorStream() for anything that isn't in the 200s
}
