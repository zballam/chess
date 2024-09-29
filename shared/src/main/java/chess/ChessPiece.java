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
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        String s;
        if (type == PieceType.BISHOP) { s = "b"; }
        else if (type == PieceType.KING) { s = "k"; }
        else if (type == PieceType.KNIGHT) { s = "n"; }
        else if (type == PieceType.PAWN) { s = "p"; }
        else if (type == PieceType.QUEEN) { s = "q"; }
        else { s = "r"; } // ROOK
        if (pieceColor == ChessGame.TeamColor.WHITE) { return s.toUpperCase(); }
        else { return s; }
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
        Collection<ChessMove> calcMoves = new ArrayList<>();
        MovesCalc movesCalc = null;

        if (type == PieceType.BISHOP) { movesCalc = new BishopCalc(); }
        else if (type == PieceType.KING) { movesCalc = new KingCalc(); }
        else if (type == PieceType.KNIGHT) { movesCalc = new KnightCalc(); }
        else if (type == PieceType.PAWN) { movesCalc = new PawnCalc(); }
        else if (type == PieceType.QUEEN) { movesCalc = new QueenCalc(); }
        else { movesCalc = new RookCalc(); } // ROOK

        calcMoves.addAll(movesCalc.pieceMoves(board, myPosition));

//        System.out.println("Board:");
//        System.out.println(board);
        return calcMoves;
    }
}
