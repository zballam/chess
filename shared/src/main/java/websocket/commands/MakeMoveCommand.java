package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    private final ChessMove moveCommand;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove moveCommand) {
        super(commandType, authToken, gameID);
        this.moveCommand = moveCommand;
    }

    public ChessMove getMoveCommand() {
        return moveCommand;
    }
}
