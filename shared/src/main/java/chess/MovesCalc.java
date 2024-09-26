package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MovesCalc {

    // Checks to see if new position is in the board
    public boolean inBoard(ChessPosition newPosition) {
        boolean inBoard = true;
        int row = newPosition.getRow();
        int col = newPosition.getColumn();
        if (row > 8 || row < 1 || col > 8 || col < 1) {
            inBoard = false;
        }
        return inBoard;
    }

    // Checks to see if new position is on enemy
    public boolean onEnemy(ChessPosition myPosition, ChessPosition newPosition, ChessBoard board) {
        boolean onEnemy = false;
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
            onEnemy = true;
        }
        return onEnemy;
    }

    // Returns one move if valid
    public Collection<ChessMove> basicMove(int rowChange, int colChange, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        // Create new position
        ChessPosition newPosition = new ChessPosition(myPosition.getRow() + rowChange, myPosition.getColumn() + colChange);

        // Check to see if on board
        if (!inBoard(newPosition)) { return calcMoves; }

        // Check to see if on piece
        if (board.getPiece(newPosition) != null) {
            // Check to see if on enemy
            if (onEnemy(myPosition, newPosition, board)) {
                calcMoves.add(new ChessMove(myPosition, newPosition));
            }
        }
        else { calcMoves.add(new ChessMove(myPosition, newPosition)); }

        return calcMoves;
    }

    // Loops through basicMove
    public Collection<ChessMove> extMove(int rowChange, int colChange, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        // Create new position
        ChessPosition newPosition = new ChessPosition(myPosition.getRow() + rowChange, myPosition.getColumn() + colChange);

        while (true) {
            // Check to see if on board
            if (!inBoard(newPosition)) {
                return calcMoves;
            }
            // Check to see if on piece
            if (board.getPiece(newPosition) != null) {
                // Check to see if on enemy
                if (onEnemy(myPosition, newPosition, board)) {
                    calcMoves.add(new ChessMove(myPosition, newPosition));
                }
                return calcMoves;
            } else {
                calcMoves.add(new ChessMove(myPosition, newPosition));
            }
            // Reset for loop
            newPosition = new ChessPosition(newPosition.getRow() + rowChange, newPosition.getColumn() + colChange);
        }
    }

    // Returns all possible piece moves
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();

        return calcMoves;
    }
}
