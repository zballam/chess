package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    public PieceType type;
    public String pieceColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor.toString();
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
        throw new RuntimeException("Not implemented");
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

        return movesCalc.pieceMoves(board, myPosition);
    }
}
