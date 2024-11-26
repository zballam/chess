package net;

public class WebsocketCommunicator extends Endpoint {
    public WebsocketCommunicator(String url) {
        try {
            url.replace("http","ws");
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
