package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] squares = new ChessPiece[8][8]; // Keeps track of the position of a piece

    public ChessBoard() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        StringBuilder full = new StringBuilder();
        StringBuilder part = new StringBuilder();
        for (ChessPiece[] row : squares) {
            part.append("|");
            for (ChessPiece piece : row) {
                if (piece != null) {
                    part.append(piece);
                }
                else {part.append(" ");}
                part.append("|");
            }
            part.append("\n");
            full.insert(0, part);
            part.setLength(0);
        }
        return full.toString();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece; // Adds the position to the piece
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (squares[position.getRow()-1][position.getColumn()-1] != null) {return squares[position.getRow()-1][position.getColumn()-1];}
        return null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ArrayList<String> pattern = new ArrayList<>(Arrays.asList("r","n","b","q","k","b","n","r"));
        int row = 1;
        int column = 1;
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;
        ChessPiece piece = null;
        // WHITE LINE
        // First line
        for (int i = 0; i < 8; i++) {
            // Decide which piece to add
            if (pattern.get(i) == "r") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK); }
            else if (pattern.get(i) == "n") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT); }
            else if (pattern.get(i) == "b") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP); }
            else if (pattern.get(i) == "q") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN); }
            else if (pattern.get(i) == "k") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.KING); }
            ChessPosition position = new ChessPosition(row, column);
            this.addPiece(position, piece);
            column += 1;
        }
        // Pawn Line
        row = 2;
        column = 1;
        piece = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        for (int i = 0; i < 8; i++) {
            ChessPosition position = new ChessPosition(row, column);
            this.addPiece(position, piece);
            column += 1;
        }

        // BLACK SIDE
        row = 8;
        column = 1;
        teamColor = ChessGame.TeamColor.BLACK;
        // First line
        for (int i = 0; i < 8; i++) {
            // Decide which piece to add
            if (pattern.get(i) == "r") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK); }
            else if (pattern.get(i) == "n") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT); }
            else if (pattern.get(i) == "b") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP); }
            else if (pattern.get(i) == "q") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN); }
            else if (pattern.get(i) == "k") { piece = new ChessPiece(teamColor, ChessPiece.PieceType.KING); }
            ChessPosition position = new ChessPosition(row, column);
            this.addPiece(position, piece);
            column += 1;
        }
        // Pawn Line
        row = 7;
        column = 1;
        piece = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        for (int i = 0; i < 8; i++) {
            ChessPosition position = new ChessPosition(row, column);
            this.addPiece(position, piece);
            column += 1;
        }

        //System.out.println("Board:\n" + this.toString());
    }
}
