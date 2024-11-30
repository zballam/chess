package server.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

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

    public void broadcast(int gameID, Session session, String message) throws IOException {
        Vector<Session> broadcastConnections = new Vector<>();
        // Determine which connections to broadcast to
        // If session is null then send to all connections
        if (session == null) {
            broadcastConnections.addAll(connections.get(gameID));
        }
        else {
            for (Session connection : connections.get(gameID)) {
                if (!connection.equals(session)) {
                    broadcastConnections.add(connection);
                }
            }
        }
        // Send message to broadcastConnections
        for (Session sendConn : broadcastConnections) {
            sendConn.getRemote().sendString(message);
        }
    }
}
