package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc extends PieceMovesCalc{

    public Collection<ChessMove> promote(int row, int col, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        calcMoves.addAll(basicMove(board, row, col, myPosition, ChessPiece.PieceType.QUEEN));
        calcMoves.addAll(basicMove(board, row, col, myPosition, ChessPiece.PieceType.ROOK));
        calcMoves.addAll(basicMove(board, row, col, myPosition, ChessPiece.PieceType.BISHOP));
        calcMoves.addAll(basicMove(board, row, col, myPosition, ChessPiece.PieceType.KNIGHT));
        return calcMoves;
    }

    /**
     * Calculates possible Pawn moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        boolean initialMove = false;
        boolean promotion = false;
        boolean black = false;

        // Figure out which direction the piece is traveling
        ChessGame.TeamColor teamColor = board.getPiece(myPosition).getTeamColor();
        if (teamColor == ChessGame.TeamColor.BLACK) {black = true;}

        if (black) {
            // Check if pawn is at the start position
            if (myPosition.getRow() == 7) {initialMove = true;}
            else if (myPosition.getRow() == 2) {promotion = true;}
            if (board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn())) == null) {
                if (promotion) {calcMoves.addAll(promote(-1,0,board,myPosition));}
                else {calcMoves.addAll(basicMove(board, -1, 0, myPosition));}
            }
            // If first move let it move twice forward
            if (initialMove
                    && board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn())) == null
                    && board.getPiece(new ChessPosition(myPosition.getRow()-2, myPosition.getColumn())) == null)
                {calcMoves.addAll(basicMove(board, -2, 0, myPosition));}
            // Check attacking positions
            ChessPosition attack1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            ChessPosition attack2 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            if (myPosition.getColumn()+1 <= 7 && board.getPiece(attack1) != null) {
                if (promotion) {calcMoves.addAll(promote(-1,1,board,myPosition));}
                else {calcMoves.addAll(basicMove(board, -1, 1, myPosition));}
            }
            if (myPosition.getColumn()-1 >= 1 && board.getPiece(attack2) != null) {
                if (promotion) {calcMoves.addAll(promote(-1,-1,board,myPosition));}
                else {calcMoves.addAll(basicMove(board, -1, -1, myPosition));}
            }
        }
        else {
            // Check if pawn is at the start position
            if (myPosition.getRow() == 2) {initialMove = true;}
            else if (myPosition.getRow() == 7) {promotion = true;}
            if (board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn())) == null) {
                if (promotion) {calcMoves.addAll(promote(1,0,board,myPosition));}
                else {calcMoves.addAll(basicMove(board, 1, 0, myPosition));}
            }
            // If first move let it move twice forward
            if (initialMove
                    && board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn())) == null
                    && board.getPiece(new ChessPosition(myPosition.getRow()+2, myPosition.getColumn())) == null )
                {calcMoves.addAll(basicMove(board, 2, 0, myPosition));}
            // Check attacking positions
            ChessPosition attack1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
            ChessPosition attack2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
            if (myPosition.getColumn()+1 <= 7 && board.getPiece(attack1) != null) {
                if (promotion) {calcMoves.addAll(promote(1,1,board,myPosition));}
                else {calcMoves.addAll(basicMove(board, 1, 1, myPosition));}
            }
            if (myPosition.getColumn()-1 >= 1 && board.getPiece(attack2) != null) {
                if (promotion) {calcMoves.addAll(promote(1,-1,board,myPosition));}
                else {calcMoves.addAll(basicMove(board, 1, -1, myPosition));}
            }
        }

        return calcMoves;
    }
}
