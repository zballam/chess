package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightCalc extends MovesCalc{

    // Returns all possible piece moves
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();

//        System.out.println("Knight");

        // Up-R
        calcMoves.addAll(basicMove(2,1, board, myPosition));
        // Up-L
        calcMoves.addAll(basicMove(2,-1, board, myPosition));
        // Down-R
        calcMoves.addAll(basicMove(-2,1, board, myPosition));
        // Down-L
        calcMoves.addAll(basicMove(-2,-1, board, myPosition));
        // R-Up
        calcMoves.addAll(basicMove(1,2, board, myPosition));
        // R-Down
        calcMoves.addAll(basicMove(-1,2, board, myPosition));
        // L-Up
        calcMoves.addAll(basicMove(1,-2, board, myPosition));
        // L-Down
        calcMoves.addAll(basicMove(-1,-2, board, myPosition));

        return calcMoves;
    }
}
