package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Determines which PieceMovesCalculator to call
 */
public class PieceMovesCalc {
    /**
     * Determines which MovesCalc to call
     *
     * @param board
     * @param myPosition
     * @param pieceType
     * @return
     */
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessPiece.PieceType pieceType) {
        ArrayList<ChessMove> calculatedMoves = new ArrayList<>();
        // Create the correct calculator based on the piece type
        if (pieceType == ChessPiece.PieceType.KING) { KingMovesCalc calc = new KingMovesCalc(); calculatedMoves = calc.pieceMoves(board, myPosition);}
        else if (pieceType == ChessPiece.PieceType.QUEEN) {}
        else if (pieceType == ChessPiece.PieceType.BISHOP) {}
        else if (pieceType == ChessPiece.PieceType.KNIGHT) {}
        else if (pieceType == ChessPiece.PieceType.ROOK) {}
        else if (pieceType == ChessPiece.PieceType.PAWN) {}

        return calculatedMoves;
    }
}
