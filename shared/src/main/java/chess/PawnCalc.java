package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalc extends MovesCalc{

    // Returns all possible pawn moves
    public Collection<ChessMove> pawnMove(ChessBoard board, ChessPosition myPosition, boolean initial) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        // What direction is pawn traveling
        int x = 1;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) { x = -1; }

        // Create new positions
        ChessPosition newPosition = new ChessPosition(myPosition.getRow() + x, myPosition.getColumn());
        ChessPosition initialMovePosition = new ChessPosition(myPosition.getRow() + x + x, myPosition.getColumn());
        ChessPosition attackR = new ChessPosition(myPosition.getRow() + x, myPosition.getColumn() + 1);
        ChessPosition attackL = new ChessPosition(myPosition.getRow() + x, myPosition.getColumn() - 1);

        // Check to see if newPosition on board
        if (inBoard(newPosition)) {
            // Check to see if newPosition on piece
            if (board.getPiece(newPosition) != null) {
                // Check to see if initialMovePosition not on piece
                if (board.getPiece(newPosition) == null) {
                    calcMoves.add(new ChessMove(myPosition, newPosition));
                }
            }
            else { calcMoves.add(new ChessMove(myPosition, newPosition)); }
        }

        // Check to see if initial
        if (initial) {
            // Check to see if newPosition not on piece
            if (board.getPiece(newPosition) == null) {
                // Check to see if initialMovePosition not on piece
                if (board.getPiece(initialMovePosition) == null) {
                    calcMoves.add(new ChessMove(myPosition, initialMovePosition));
                }
            }
        }

        // Check to see if attackR on board
        if (inBoard(attackR)) {
            // Check to see if attackR on piece
            if (board.getPiece(attackR) != null) {
                if (onEnemy(myPosition, attackR, board)) {
                    calcMoves.add(new ChessMove(myPosition, attackR));
                }
            }
        }

        // Check to see if attackL on board
        if (inBoard(attackL)) {
            // Check to see if attackL on piece
            if (board.getPiece(attackL) != null) {
                if (onEnemy(myPosition, attackL, board)) {
                    calcMoves.add(new ChessMove(myPosition, attackL));
                }
            }
        }

        return calcMoves;
    }

    // Takes pawn move and promotes them
    public Collection<ChessMove> promotionMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        Collection<ChessMove> promoMoves = new ArrayList<>();
        calcMoves.addAll(pawnMove(board, myPosition, false));
        // Loop through each move in calcMoves create a new move with a promotion piece
        for (ChessMove move : calcMoves) {
            promoMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.BISHOP));
            promoMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.KNIGHT));
            promoMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.QUEEN));
            promoMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.ROOK));
        }
        return promoMoves;
    }

    // Returns all possible piece moves
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int initialRow = 2;
        int promotionRow = 7;

        // Figure out which direction pawn is going
        if (pieceColor == ChessGame.TeamColor.BLACK) {
            initialRow = 7;
            promotionRow = 2;
        }

        // Check to see if at initial spot
        if (myPosition.getRow() == initialRow) { calcMoves.addAll(pawnMove(board, myPosition, true)); }
        // Check to see if at promotion spot
        else if (myPosition.getRow() == promotionRow) { calcMoves.addAll(promotionMove(board, myPosition)); }
        // Otherwise
        else { calcMoves.addAll(pawnMove(board, myPosition, false)); }

        return calcMoves;
    }
}
