package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalc extends PieceMovesCalc {

    /**
     * Calculates possible Bishop moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        System.out.println("Board:\n" + board.toString());

        System.out.println("Bishop moves");

        // Bottom-left moves
        System.out.println("Bottom-Left: ");
        calcMoves.addAll(findMoves(board,-1,-1,myPosition));

        // Top-left moves
        System.out.println("Top-Left: ");
        calcMoves.addAll(findMoves(board,1,-1,myPosition));

        // Top-right moves
        System.out.println("Top-Right: ");
        calcMoves.addAll(findMoves(board,1,1,myPosition));

        // Bottom-right moves
        System.out.println("Top-Right: ");
        calcMoves.addAll(findMoves(board,-1,1,myPosition));

        System.out.println(calcMoves);
        return calcMoves;
    }
}
