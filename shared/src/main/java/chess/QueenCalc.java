package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenCalc extends MovesCalc{

    // Returns all possible piece moves
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();

        // Up
        calcMoves.addAll(extMove(1,0, board, myPosition));
        // Down
        calcMoves.addAll(extMove(-1,0, board, myPosition));
        // Left
        calcMoves.addAll(extMove(0,-1, board, myPosition));
        // Right
        calcMoves.addAll(extMove(0,1, board, myPosition));
        // Up-R
        calcMoves.addAll(extMove(1,1, board, myPosition));
        // Up-L
        calcMoves.addAll(extMove(1,-1, board, myPosition));
        // Down-R
        calcMoves.addAll(extMove(-1,1, board, myPosition));
        // Down-L
        calcMoves.addAll(extMove(-1,-1, board, myPosition));

        return calcMoves;
    }
}
