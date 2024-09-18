package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc extends PieceMovesCalc {

    /**
     * Calculates possible Queen moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        System.out.println("Board:\n" + board.toString());
        // Left moves
        calcMoves.addAll(findMoves(board,-1,0,myPosition));
        // Right moves
        calcMoves.addAll(findMoves(board,1,0,myPosition));
        // Up moves
        calcMoves.addAll(findMoves(board,0,1,myPosition));
        // Down moves
        calcMoves.addAll(findMoves(board,0,-1,myPosition));
        // Bottom-left moves
        calcMoves.addAll(findMoves(board,-1,-1,myPosition));
        // Top-left moves
        calcMoves.addAll(findMoves(board,1,-1,myPosition));
        // Top-right moves
        calcMoves.addAll(findMoves(board,1,1,myPosition));
        // Bottom-right moves
        calcMoves.addAll(findMoves(board,-1,1,myPosition));
        //System.out.println(calcMoves);
        return calcMoves;
    }
}
