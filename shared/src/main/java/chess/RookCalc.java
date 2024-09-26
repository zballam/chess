package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookCalc extends MovesCalc{

    // Returns all possible piece moves
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();

//        System.out.println("Rook");

        // Up
        calcMoves.addAll(extMove(1,0, board, myPosition));
        // Down
        calcMoves.addAll(extMove(-1,0, board, myPosition));
        // Left
        calcMoves.addAll(extMove(0,-1, board, myPosition));
        // Right
        calcMoves.addAll(extMove(0,1, board, myPosition));

        return calcMoves;
    }
}
