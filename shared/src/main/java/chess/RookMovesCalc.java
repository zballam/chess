package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc extends PieceMovesCalc {

    /**
     * Calculates possible Knight moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
//        System.out.println("Board:\n" + board.toString());
        // Left moves
        calcMoves.addAll(findMoves(board,-1,0,myPosition));
        // Right moves
        calcMoves.addAll(findMoves(board,1,0,myPosition));
        // Up moves
        calcMoves.addAll(findMoves(board,0,1,myPosition));
        // Down moves
        calcMoves.addAll(findMoves(board,0,-1,myPosition));
        //System.out.println(calcMoves);
        return calcMoves;
    }
}
