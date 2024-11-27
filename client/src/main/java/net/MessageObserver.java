package net;

import websocket.messages.ServerMessage;

public interface MessageObserver {
    // receives messages from websocketCommunicator and handles them
    void notify(ServerMessage message);
}
