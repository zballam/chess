package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Determines which PieceMovesCalculator to call
 */
public class PieceMovesCalc {

    public boolean inBoard(ChessPosition position) {
        boolean result = true;
        if (position.getColumn() > 8 || position.getRow() > 8 || position.getColumn() < 8 || position.getRow() < 8) {
            result = false;
        }
        return result;
    }

    /**
     * Determines which MovesCalc to call
     *
     * @param board
     * @param myPosition
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calculatedMoves = new ArrayList<ChessMove>();
        return calculatedMoves;
    }
}
