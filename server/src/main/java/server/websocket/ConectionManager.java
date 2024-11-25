package server.websocket;

import java.util.HashMap;
import org.eclipse.jetty.websocket.api.Session;

public class ConectionManager {
    public final HashMap<String, Session> connections = new HashMap<>();

    public void add(String playerName, Session session) {
        connections.put(playerName, session);
    }

    public void remove(String playerName) {
        connections.remove(playerName);
    }

    public void broadcast() {
        throw new RuntimeException("Broadcast method not implemented.");
    }
}
