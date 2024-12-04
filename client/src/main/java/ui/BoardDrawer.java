package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        ChessMove move = new ChessMove(new ChessPosition(1,1), new ChessPosition(2,1));
        Collection<ChessMove> moves = new ArrayList<>();
        moves.add(move);
        highlightMoves(moves, pieces, white);
//        drawChessBoard(pieces, white);
//        drawChessBoard(pieces, black);
    }

    private static Set<String> convertHighlightMoves(Collection<ChessMove> collectionMoves) {
        Set<String> highlightSquares = new HashSet<>();
        if (collectionMoves != null) {
            for (ChessMove move : collectionMoves) {
                int destRow1 = move.getStartPosition().getRow()-1;
                int destCol1 = move.getStartPosition().getColumn()-1;
                int destRow2 = move.getEndPosition().getRow()-1;
                int destCol2 = move.getEndPosition().getColumn()-1;
                highlightSquares.add(destRow1 + "," + destCol1); // Use "row,col" as the key
                highlightSquares.add(destRow2 + "," + destCol2);
            }
        }
        else {
            highlightSquares = null;
        }
        return highlightSquares;
    }

    public static void highlightMoves(Collection<ChessMove> highlightMoves, ChessPiece[][] pieces, ChessGame.TeamColor team) {
        Set<String> highlightSquares = convertHighlightMoves(highlightMoves);
        PrintStream out = OUTSTREAM;
        out.print(ERASE_SCREEN);
        boolean blackTeam;
        blackTeam = !(team == ChessGame.TeamColor.WHITE);
        out.println();
        drawHeaders(out, blackTeam);
        drawRows(pieces, out, blackTeam, highlightSquares);
        drawHeaders(out, blackTeam);
    }

    public static void drawChessBoard(ChessPiece[][] pieces, ChessGame.TeamColor team) {
        PrintStream out = OUTSTREAM;
        out.print(ERASE_SCREEN);
        boolean blackTeam;
        blackTeam = !(team == ChessGame.TeamColor.WHITE);
        out.println();
        drawHeaders(out, blackTeam);
        drawRows(pieces, out, blackTeam, null);
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

    private static void drawRows(ChessPiece[][] pieces, PrintStream out, boolean blackTeam, Set<String> highlightMoves) {
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
                drawRow(out, reversedArray, whiteSquare, highlightMoves, x);
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
                drawRow(out, pieces[x], whiteSquare, highlightMoves, x);
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

    private static void drawRow(PrintStream out, ChessPiece[] pieces, boolean whiteSquare, Set<String> highlightMoves, int row) {
        if (highlightMoves == null) {
            for (ChessPiece piece : pieces) {
                if (piece != null) {
                    drawSquare(out, piece, whiteSquare);
                } else {
                    drawSquare(out, null, whiteSquare);
                }
                whiteSquare = !(whiteSquare);
            }
        }
        else {
            for (int col = 0; col < pieces.length; col++) {
                String squareKey = row + "," + col;
                if (highlightMoves.contains(squareKey)) {
                    if (pieces[col] != null) {
                        highlightSquare(out, pieces[col], whiteSquare);
                    }
                    else {
                        highlightSquare(out, null, whiteSquare);
                    }
                }
                else {
                    if (pieces[col] != null) {
                        drawSquare(out, pieces[col], whiteSquare);
                    } else {
                        drawSquare(out, null, whiteSquare);
                    }
                }
                whiteSquare = !(whiteSquare);
            }
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

    private static void drawGreenSquare(PrintStream out, ChessPiece piece) {
        if (piece != null) {
            boolean white = piece.getTeamColor() == ChessGame.TeamColor.WHITE;
            setWhite(out);
            setGreen(out);
            setPieceColor(out, white);
            printString(out, piece.toString());
            setWhite(out);
            resetColor(out);
        }
        else {
            setWhite(out);
            setGreen(out);
            printString(out, " ");
            setWhite(out);
            resetColor(out);
        }
    }

    private static void drawDarkGreenSquare(PrintStream out, ChessPiece piece) {
        if (piece != null) {
            boolean white = piece.getTeamColor() == ChessGame.TeamColor.WHITE;
            setBlack(out);
            setDarkGreen(out);
            setPieceColor(out, white);
            printString(out, piece.toString());
            setBlack(out);
            resetColor(out);
        }
        else {
            setBlack(out);
            setDarkGreen(out);
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

    private static void highlightSquare(PrintStream out, ChessPiece piece, boolean whiteSquare) {
        if (whiteSquare) {
            drawGreenSquare(out, piece);
        }
        else {
            drawDarkGreenSquare(out, piece);
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

    private static void setDarkGreen(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
    }

    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
    }
}
