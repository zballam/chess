package ui;

import chess.ChessGame;
import net.ServerFacade;

public class GameClient {
    ServerFacade serverFacade;
    private final BoardDrawer drawer = new BoardDrawer();

    public GameClient(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    public String run(String command) {
        // This is all temporary. For now just print the boards
        ChessGame game = new ChessGame();
        drawer.drawChessBoard(game.getBoard().getSquares(), ChessGame.TeamColor.WHITE);
        drawer.drawChessBoard(game.getBoard().getSquares(), ChessGame.TeamColor.BLACK);
        return "";
    }
}
