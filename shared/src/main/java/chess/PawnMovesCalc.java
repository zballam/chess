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
        Collection<ChessMove> calcMoves = new ArrayList<>();
        System.out.println(myPosition);
        System.out.println(board);
        return calcMoves;
    }
}
