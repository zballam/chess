package websocket.messages;

import chess.ChessGame;
import chess.ChessMove;

public class LoadGameMessage extends ServerMessage{
    private final ChessGame game;
    private final ChessMove lastMove;

    public LoadGameMessage(ChessGame game, ChessMove lastMove) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
        this.lastMove = lastMove;
    }

    public ChessGame getGame() {
        return game;
    }

    public ChessMove getLastMove() {
        return lastMove;
    }
}
