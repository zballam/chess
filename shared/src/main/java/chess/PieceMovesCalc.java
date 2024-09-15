package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Determines which PieceMovesCalculator to call
 */
public class PieceMovesCalc {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean inBoard(ChessPosition position) {
        boolean result = true;
        if (position.getColumn() > 8 || position.getRow() > 8 || position.getColumn() < 1 || position.getRow() < 1) {
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
        Collection<ChessMove> calculatedMoves = new ArrayList<>();
        return calculatedMoves;
    }
}
