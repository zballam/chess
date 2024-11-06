package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.util.Collection;

public class BoardDrawer {
    // This draws the board in the terminal
    public static void main(String[] args) {

    }

    private static void drawChessBoard(Collection<ChessPiece> pieces, ChessGame.TeamColor team) {
        boolean whiteTeam;
        if (team == ChessGame.TeamColor.WHITE) { whiteTeam = true; }
        else { whiteTeam = false; }

    }

    private static void drawHeaders(boolean whiteTeam) {

    }

    private static void drawWhiteSquare() {

    }

    private static void drawBlackSquare() {

    }
}
