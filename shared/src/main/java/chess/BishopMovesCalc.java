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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<ChessMove>();
        System.out.println("Bishop moves");
        ChessPosition positionHolder = myPosition;

        int x = positionHolder.getColumn() - 1;
        int y = positionHolder.getRow() - 1;
        System.out.println("Position: " + positionHolder);

//        // Bottom-left moves
//        while (true) {
//            int x = positionHolder.getColumn() - 1;
//            int y = positionHolder.getRow() - 1;
//            ChessPosition newPositionHolder = new ChessPosition(x, y);
//
//            // If not a valid position then leave the loop
//            if (!inBoard(newPositionHolder)) {break;}
//            else {
//                ChessMove newMove = new ChessMove(myPosition, newPositionHolder, null);
//                calcMoves.add(newMove);
//            }
        //}

        return calcMoves;
    }
}
