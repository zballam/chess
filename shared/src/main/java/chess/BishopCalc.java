package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopCalc extends MovesCalc {

    // Returns all possible piece moves
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();

//        System.out.println("Bishop");

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
