package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable {
    private ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {
        
    }

    @Override
    public ChessBoard clone() {
        try {
            ChessBoard clone = (ChessBoard) super.clone();
            // Clone squares
            ChessPiece[][] clonedSquares = new ChessPiece[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    clonedSquares[i][j] = squares[i][j];
                }
            }
            clone.squares = clonedSquares;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        StringBuilder helper = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            helper = new StringBuilder();
            helper.append("|");
            for (int j = 0; j < 8; j++) {
                if (squares[i][j] != null) {
                    helper.append(squares[i][j].toString());
                }
                else {
                    helper.append(" ");
                }
                helper.append("|");
            }
            helper.append("\n");
            builder.insert(0,helper);
        }

        return builder.toString();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Removes a piece from the board
     *
     * @param position where to remove piece from
     */
    public void removePiece(ChessPosition position) {
        squares[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Implements a move on the board
     *
     * @param move the move to implement
     */
    public void doMove(ChessMove move) {
        // Get new/moved piece
        ChessPiece piece = null;
        if (move.getPromotionPiece() == null) {
            piece = getPiece(move.getStartPosition());
        }
        else {
            piece = new ChessPiece(getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece());
        }
        squares[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = piece;
        removePiece(move.getStartPosition()); // Removes the piece from its start position
    }




    public void makeRow(int row) {
        ChessGame.TeamColor pieceColor = ChessGame.TeamColor.WHITE;
        if (row == 8) { pieceColor = ChessGame.TeamColor.BLACK; }
        addPiece(new ChessPosition(row,1), new ChessPiece(pieceColor, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(row,2), new ChessPiece(pieceColor, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(row,3), new ChessPiece(pieceColor, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(row,4), new ChessPiece(pieceColor, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(row,5), new ChessPiece(pieceColor, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(row,6), new ChessPiece(pieceColor, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(row,7), new ChessPiece(pieceColor, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(row,8), new ChessPiece(pieceColor, ChessPiece.PieceType.ROOK));
    }

    public void makePawns(int row) {
        ChessGame.TeamColor pieceColor = ChessGame.TeamColor.WHITE;
        if (row == 7) { pieceColor = ChessGame.TeamColor.BLACK; }
        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPosition(row,i+1), new ChessPiece(pieceColor, ChessPiece.PieceType.PAWN));
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Make bottom
        makeRow(1);
        // Make white pawns
        makePawns(2);
        // Make top
        makeRow(8);
        // Make black pawns
        makePawns(7);
    }
}
