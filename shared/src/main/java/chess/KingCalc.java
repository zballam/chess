package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalc extends MovesCalc{

    // Returns all possible piece moves
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();

//        System.out.println("King");

        // Up
        calcMoves.addAll(basicMove(1,0, board, myPosition));
        // Down
        calcMoves.addAll(basicMove(-1,0, board, myPosition));
        // Left
        calcMoves.addAll(basicMove(0,-1, board, myPosition));
        // Right
        calcMoves.addAll(basicMove(0,1, board, myPosition));
        // Up-R
        calcMoves.addAll(basicMove(1,1, board, myPosition));
        // Up-L
        calcMoves.addAll(basicMove(1,-1, board, myPosition));
        // Down-R
        calcMoves.addAll(basicMove(-1,1, board, myPosition));
        // Down-L
        calcMoves.addAll(basicMove(-1,-1, board, myPosition));

        return calcMoves;
    }
}
