package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc extends PieceMovesCalc{
    boolean initialMove = false;

    /**
     * Calculates possible Pawn moves
     *
     * @param board
     * @param myPosition
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> calcMoves = new ArrayList<>();
        boolean black = false;

        // Figure out which direction the piece is traveling
        ChessGame.TeamColor teamColor = board.getPiece(myPosition).getTeamColor();
        if (teamColor == ChessGame.TeamColor.BLACK) {black = true;}
        System.out.println(teamColor.toString() + " piece");
        System.out.println("Board:\n" + board.toString());

        if (black) {
            if (board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn())) == null) {calcMoves.addAll(basicMove(board, -1, 0, myPosition));}
            // Check if pawn is at the start position
            if (myPosition.getRow() == 7) {initialMove = true;}
            // If first move let it move twice forward
            if (initialMove
                    && board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn())) == null
                    && board.getPiece(new ChessPosition(myPosition.getRow()-2, myPosition.getColumn())) == null)
                {calcMoves.addAll(basicMove(board, -2, 0, myPosition));}
            // Check attacking positions
            ChessPosition attack1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            ChessPosition attack2 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            if (board.getPiece(attack1) != null) {calcMoves.addAll(basicMove(board, -1, 1, myPosition));}
            if (board.getPiece(attack2) != null) {calcMoves.addAll(basicMove(board, -1, -1, myPosition));}
        }
        else {
            if (board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn())) == null) {calcMoves.addAll(basicMove(board, 1, 0, myPosition));}
            // Check if pawn is at the start position
            if (myPosition.getRow() == 2) {initialMove = true;}
            // If first move let it move twice forward
            if (initialMove
                    && board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn())) == null
                    && board.getPiece(new ChessPosition(myPosition.getRow()+2, myPosition.getColumn())) == null )
                {calcMoves.addAll(basicMove(board, 2, 0, myPosition));}
            // Check attacking positions
            ChessPosition attack1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
            ChessPosition attack2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
            if (board.getPiece(attack1) != null) {calcMoves.addAll(basicMove(board, 1, 1, myPosition));}
            if (board.getPiece(attack2) != null) {calcMoves.addAll(basicMove(board, 1, -1, myPosition));}
        }

        return calcMoves;
    }
}
