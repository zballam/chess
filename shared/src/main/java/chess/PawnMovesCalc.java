package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc extends PieceMovesCalc{

    /**
     * Calculates possible Pawn moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        Collection<ChessMove> calcMoves = new ArrayList<>();
//        System.out.println("Board:\n" + board.toString());
//        // Up move
//        calcMoves.addAll(basicMove(board,0,1,myPosition));
//        // Down move
//        calcMoves.addAll(basicMove(board,0,-1,myPosition));

        // IF PAWN HASN'T MOVED YET LET IT MOVE TWICE FOR THE FIRST MOVE
        return calcMoves;
    }
}
