package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    private final ChessMove moveCommand;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove moveCommand) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.moveCommand = moveCommand;
    }

    public ChessMove getMoveCommand() {
        return moveCommand;
    }
}
