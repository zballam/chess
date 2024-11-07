package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ui.EscapeSequences.*;

public class BoardDrawer {
    private static final String EMPTY = "   ";
    private static final String PADDING = " ";
    private static final String WHITETEAM = SET_TEXT_COLOR_BLUE;
    private static final String BLACKTEAM = SET_TEXT_COLOR_MAGENTA;


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        Collection<ChessPiece> pieces = new ArrayList<>();
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
        drawChessBoard(pieces, out, white);
        drawChessBoard(pieces, out, black);


    }

    private static void drawChessBoard(Collection<ChessPiece> pieces, PrintStream out, ChessGame.TeamColor team) {
        out.print(ERASE_SCREEN);
        boolean whiteTeam;
        whiteTeam = team == ChessGame.TeamColor.WHITE;
        drawHeaders(out, whiteTeam);
    }

    private static void drawHeaders(PrintStream out, boolean whiteTeam) {
        List<String> headers = new ArrayList<>(List.of("a","b","c","d","e","f","g","h"));
        setLightGray(out);
        out.print(EMPTY);
        if (whiteTeam) {
            for (String header : headers) {
                printString(out, header);
            }
        }
        else {
            for (int i = headers.size() - 1; i >= 0; i--) {
                printString(out, headers.get(i));
            }
        }
        setLightGray(out);
        out.print(EMPTY);
        resetColor(out);
        out.println();
    }

    private static void drawRow(Collection<ChessPiece> pieces, PrintStream out, boolean whiteTeam) {
        List<String> colHeaders = new ArrayList<>(List.of("1","2","3","4","5","6","7","8"));
        setLightGray(out);
        if (whiteTeam) {
            for (String colHeader : colHeaders) {
                drawColHeader(out, colHeader);
            }
        }
    }

    private static void drawColHeader(PrintStream out, String header) {

    }

    private static void setPieceColor(PrintStream out, boolean white) {
        if (white) {
            out.print(WHITETEAM);
        }
        else {
            out.print(BLACKTEAM);
        }
    }

    private static void drawWhiteSquare(PrintStream out, String piece, boolean white) {
        setPieceColor(out, white);
    }

    private static void drawBlackSquare(PrintStream out, String piece, boolean white) {
        setPieceColor(out, white);
    }

    private static void printString(PrintStream out, String string) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(PADDING);
        out.print(string);
        out.print(PADDING);
    }

    private static void resetColor(PrintStream out) {
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    private static void setLightGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}
