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
        System.out.println(myPosition);
        System.out.println(board);
        return calcMoves;
    }
}