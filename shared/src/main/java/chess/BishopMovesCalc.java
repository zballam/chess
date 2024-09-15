package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalc extends PieceMovesCalc {

    public Collection<ChessMove> findMoves(int xChange, int yChange, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPosition ogPosition = myPosition;
        while (true) {
            int x = myPosition.getRow() + xChange;
            int y = myPosition.getColumn() + yChange;
            System.out.println("x: " + x + ", y: " + y);
            ChessPosition newPosition = new ChessPosition(x, y);
            // If the newPosition is out of range then stop
            if (!inBoard(newPosition)){
                return moves;
            }
            else {
                ChessMove newMove = new ChessMove(ogPosition, newPosition, null);
                moves.add(newMove);
                myPosition = newPosition;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

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
        System.out.println("Bishop moves");

        // Bottom-left moves
        System.out.println("Bottom-Left: ");
        calcMoves.addAll(findMoves(-1,-1,myPosition));

        // Top-left moves
        System.out.println("Top-Left: ");
        calcMoves.addAll(findMoves(1,-1,myPosition));

        // Top-right moves
        System.out.println("Top-Right: ");
        calcMoves.addAll(findMoves(1,1,myPosition));

        // Bottom-right moves
        System.out.println("Top-Right: ");
        calcMoves.addAll(findMoves(-1,1,myPosition));

        System.out.println(calcMoves);
        return calcMoves;
    }
}
