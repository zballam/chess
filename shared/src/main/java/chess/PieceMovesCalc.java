package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Determines which PieceMovesCalculator to call
 */
public class PieceMovesCalc {

    public Collection<ChessMove> basicMove(ChessBoard board, int xChange, int yChange, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPosition ogPosition = myPosition;
        int x = myPosition.getRow() + xChange;
        int y = myPosition.getColumn() + yChange;
        // System.out.println("x: " + x + ", y: " + y);
        ChessPosition newPosition = new ChessPosition(x, y);
        // Check to make sure position is valid before adding it
        if (!inBoard(newPosition)){return moves;}
        // Check for another piece at that position
        if (board.getPiece(newPosition) != null){
            // System.out.println("piece found: " + board.getPiece(newPosition));
            // If the newPosition hits a piece check if enemy
            if (onEnemy(board, newPosition, ogPosition)) {
                // System.out.println("enemy piece");
                ChessMove newMove = new ChessMove(ogPosition, newPosition, null);
                moves.add(newMove);
                return moves;
            }  else { //Same team piece
            // System.out.println("same team piece");
            return moves;
            }
        }
        ChessMove newMove = new ChessMove(ogPosition, newPosition, null);
        moves.add(newMove);
        return moves;
    }


    public Collection<ChessMove> findMoves(ChessBoard board, int xChange, int yChange, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPosition ogPosition = myPosition;
        while (true) {
            int x = myPosition.getRow() + xChange;
            int y = myPosition.getColumn() + yChange;
            // System.out.println("x: " + x + ", y: " + y);
            ChessPosition newPosition = new ChessPosition(x, y);
            // If the newPosition is out of range then stop
            if (!inBoard(newPosition)){return moves;}
            // If there is a piece in the newPosition
            if (board.getPiece(newPosition) != null) {
                // System.out.println("piece found: " + board.getPiece(newPosition));
                // If the newPosition hits a piece check if enemy
                if (onEnemy(board, newPosition, ogPosition)) { //Enemy team piece
                    // System.out.println("enemy piece");
                    ChessMove newMove = new ChessMove(ogPosition, newPosition, null);
                    moves.add(newMove);
                    return moves;
                } else { //Same team piece
                    // System.out.println("same team piece");
                    return moves;
                }
            }
            else {
                ChessMove newMove = new ChessMove(ogPosition, newPosition, null);
                moves.add(newMove);
                myPosition = newPosition;
            }
        }
    }

    /**
     * Checks to see if the position is inside the bounds of the board
     *
     * @param position
     * @return
     */
    public boolean inBoard(ChessPosition position) {
        boolean result = true;
        if (position.getColumn() > 8 || position.getRow() > 8 || position.getColumn() < 1 || position.getRow() < 1) {
            result = false;
        }
        return result;
    }

    /**
     * Checks to see if there is a piece already at the position and returns boolean
     * T = enemy
     * F = same team
     *
     */
    public boolean onEnemy(ChessBoard board, ChessPosition newPosition, ChessPosition ogPosition) {
        boolean result = false;
        if (board.getPiece(newPosition).getTeamColor() != board.getPiece(ogPosition).getTeamColor()) {
            // Enemy
            result = true;
        }
        return result;
    }

    /**
     * Determines which MovesCalc to call
     *
     * @param board the Chess board
     * @param myPosition the piece position
     * @return calculatedMoves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calculatedMoves = new ArrayList<>();
        return calculatedMoves;
    }
}
