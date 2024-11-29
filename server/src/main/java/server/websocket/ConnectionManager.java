package server.websocket;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.jetty.websocket.api.Session;

public class ConnectionManager {
    public final HashMap<Integer, Set<Session>> connections = new HashMap<>();

    public void add(Integer gameID, Session session) {
        connections.get(gameID).add(session);
    }

    public void remove(Integer gameID, Session session) {
        if (connections.get(gameID) != null) {
            connections.get(gameID).remove(session);
        }
    }

    public void broadcast() {
        throw new RuntimeException("Broadcast method not implemented.");
    }
}
