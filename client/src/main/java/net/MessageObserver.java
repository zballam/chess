package net;

import chess.ChessGame;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

public interface MessageObserver {
    // receives messages from websocketCommunicator and handles them
    void notify(ServerMessage message);

    void notifyLoadGameMessage(LoadGameMessage message, ChessGame.TeamColor teamColor);
}
