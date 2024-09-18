package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType type;
    private ChessGame.TeamColor pieceColor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return type == that.type && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }



    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (type == PieceType.KING) {result.append("k");}
        else if (type == PieceType.QUEEN) {result.append("q");}
        else if (type == PieceType.BISHOP) {result.append("b");}
        else if (type == PieceType.KNIGHT) {result.append("n");}
        else if (type == PieceType.ROOK) {result.append("r");}
        else if (type == PieceType.PAWN) {result.append("p");}

        if (pieceColor == ChessGame.TeamColor.WHITE) {return result.toString().toUpperCase();}
        return result.toString();
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMovesCalc movesCalc = null;
        // Figure out which pieceCalc to use
        if (this.type == ChessPiece.PieceType.KING) {movesCalc = new KingMovesCalc();}
        else if (this.type == ChessPiece.PieceType.QUEEN) {movesCalc = new QueenMovesCalc();}
        else if (this.type == ChessPiece.PieceType.BISHOP) {movesCalc = new BishopMovesCalc();}
        else if (this.type == ChessPiece.PieceType.KNIGHT) {movesCalc = new KnightMovesCalc();}
        else if (this.type == ChessPiece.PieceType.ROOK) {movesCalc = new RookMovesCalc();}
        else if (this.type == ChessPiece.PieceType.PAWN) {movesCalc = new PawnMovesCalc();}

        assert movesCalc != null;
        return movesCalc.pieceMoves(board, myPosition);
    }
}
