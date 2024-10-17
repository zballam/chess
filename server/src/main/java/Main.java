import chess.*;
import server.*;
import dataaccess.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        boolean memoryDataAccess = true;
        Server server = new server.Server();
        server.run(8080);
    }
}