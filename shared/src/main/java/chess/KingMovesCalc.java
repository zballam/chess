package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalc extends PieceMovesCalc{

    /**
     * Calculates possible King moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        //System.out.println("Board:\n" + board.toString());
        // Up move
        calcMoves.addAll(basicMove(board,0,1,myPosition));
        // Down move
        calcMoves.addAll(basicMove(board,0,-1,myPosition));
        // Left move
        calcMoves.addAll(basicMove(board,-1,0,myPosition));
        // Right move
        calcMoves.addAll(basicMove(board,1,0,myPosition));
        // Top-right move
        calcMoves.addAll(basicMove(board,1,1,myPosition));
        // Bottom-right move
        calcMoves.addAll(basicMove(board,1,-1,myPosition));
        // Bottom-left move
        calcMoves.addAll(basicMove(board,-1,-1,myPosition));
        // Top-left move
        calcMoves.addAll(basicMove(board,-1,1,myPosition));
        //System.out.println(calcMoves);
        return calcMoves;
    }
}