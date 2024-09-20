package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc extends PieceMovesCalc{

    /**
     * Calculates possible Knight moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        //System.out.println("Board:\n" + board.toString());
        // Up-Right move
        calcMoves.addAll(basicMove(board,1,2,myPosition));
        // Up-Left move
        calcMoves.addAll(basicMove(board,-1,2,myPosition));
        // Down-Right move
        calcMoves.addAll(basicMove(board,1,-2,myPosition));
        // Down-Left move
        calcMoves.addAll(basicMove(board,-1,-2,myPosition));
        // Right-Up move
        calcMoves.addAll(basicMove(board,2,1,myPosition));
        // Right-Down move
        calcMoves.addAll(basicMove(board,2,-1,myPosition));
        // Left-Up move
        calcMoves.addAll(basicMove(board,-2,1,myPosition));
        // Left-Down move
        calcMoves.addAll(basicMove(board,-2,-1,myPosition));
        return calcMoves;
    }
}
