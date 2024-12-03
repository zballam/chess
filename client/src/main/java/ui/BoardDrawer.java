package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ui.EscapeSequences.*;

public class BoardDrawer {
    private static final String EMPTY = "   ";
    private static final String PADDING = " ";
    private static final String WHITETEAM = SET_TEXT_COLOR_BLUE;
    private static final String BLACKTEAM = SET_TEXT_COLOR_MAGENTA;

    private static final PrintStream OUTSTREAM = new PrintStream(System.out, true, StandardCharsets.UTF_8);


    public static void main(String[] args) {

        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;

        ChessGame board = new ChessGame();
        ChessPiece[][] pieces = board.getBoard().getSquares();
        drawChessBoard(pieces, white);
        drawChessBoard(pieces, black);
    }

    public static void drawChessBoard(ChessPiece[][] pieces, ChessGame.TeamColor team) {
        PrintStream out = OUTSTREAM;
        out.print(ERASE_SCREEN);
        boolean blackTeam;
        blackTeam = !(team == ChessGame.TeamColor.WHITE);
        out.println();
        drawHeaders(out, blackTeam);
        drawRows(pieces, out, blackTeam);
        drawHeaders(out, blackTeam);
    }

    private static void drawHeaders(PrintStream out, boolean blackTeam) {
        List<String> headers = new ArrayList<>(List.of("a","b","c","d","e","f","g","h"));
        setLightGray(out);
        out.print(EMPTY);
        if (!blackTeam) {
            for (String header : headers) {
                out.print(SET_TEXT_COLOR_BLACK);
                printString(out, header);
            }
        }
        else {
            for (int i = headers.size() - 1; i >= 0; i--) {
                out.print(SET_TEXT_COLOR_BLACK);
                printString(out, headers.get(i));
            }
        }
        setLightGray(out);
        out.print(EMPTY);
        resetColor(out);
        out.println();
    }

    private static void drawRows(ChessPiece[][] pieces, PrintStream out, boolean blackTeam) {
        List<String> colHeaders = new ArrayList<>(List.of("1","2","3","4","5","6","7","8"));
        setLightGray(out);
        int x = 0;
        boolean whiteSquare = true;
        if (blackTeam) {
            for (String colHeader : colHeaders) {
                drawColHeader(out, colHeader);
                // reverse the array
                ChessPiece[] array = pieces[x];
                ChessPiece[] reversedArray = new ChessPiece[array.length];
                for (int i = 0; i < array.length; i++) {
                    reversedArray[i] = array[array.length - 1 - i];
                }
                drawRow(out, reversedArray, whiteSquare);
                x++;
                setLightGray(out);
                drawColHeader(out, colHeader);
                resetColor(out);
                out.println();
                whiteSquare = !(whiteSquare);
            }
        }
        else {
            x = 7;
            for (int i = colHeaders.size() - 1; i >= 0; i--) {
                drawColHeader(out, colHeaders.get(i));
                drawRow(out, pieces[x], whiteSquare);
                x--;
                setLightGray(out);
                drawColHeader(out, colHeaders.get(i));
                resetColor(out);
                out.println();
                whiteSquare = !(whiteSquare);
            }
        }
    }

    private static void drawColHeader(PrintStream out, String header) {
        setLightGray(out);
        out.print(SET_TEXT_COLOR_BLACK);
        printString(out, header);
        resetColor(out);
    }

    private static void drawRow(PrintStream out, ChessPiece[] pieces, boolean whiteSquare) {
        for (ChessPiece piece : pieces) {
            if (piece != null) {
                drawSquare(out, piece, whiteSquare);
            }
            else {
                drawSquare(out, null, whiteSquare);
            }
            whiteSquare = !(whiteSquare);
        }
    }


    private static void setPieceColor(PrintStream out, boolean white) {
        if (white) {
            out.print(WHITETEAM);
        }
        else {
            out.print(BLACKTEAM);
        }
    }

    private static void drawWhiteSquare(PrintStream out, ChessPiece piece) {
        if (piece != null) {
            boolean white = piece.getTeamColor() == ChessGame.TeamColor.WHITE;
            setWhite(out);
            setPieceColor(out, white);
            printString(out, piece.toString());
            setWhite(out);
            resetColor(out);
        }
        else {
            setWhite(out);
            printString(out, " ");
            setWhite(out);
            resetColor(out);
        }
    }

    private static void drawBlackSquare(PrintStream out, ChessPiece piece) {
        if (piece != null) {
            boolean white = piece.getTeamColor() == ChessGame.TeamColor.WHITE;
            setBlack(out);
            setPieceColor(out, white);
            printString(out, piece.toString());
            setBlack(out);
            resetColor(out);
        }
        else {
            setBlack(out);
            printString(out, " ");
            setBlack(out);
            resetColor(out);
        }
    }

    private static void drawSquare(PrintStream out, ChessPiece piece, boolean whiteSquare) {
        if (whiteSquare) {
            drawWhiteSquare(out, piece);
        }
        else {
            drawBlackSquare(out, piece);
        }
    }

    private static void printString(PrintStream out, String string) {
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

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }
}
