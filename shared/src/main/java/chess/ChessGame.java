package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }


    // HELPER METHODS

    /**
     * Return the positions where a team has their pieces
     *
     * @param team the team in question
     * @param board the current board
     * @return a collection of all possible
     */
    public Collection<ChessPosition> currentTeamPositions(TeamColor team, ChessBoard board) {
        Collection teamPositions = new ArrayList();
        ChessPosition position;
        // Loop through board and get team positions
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // If there is a piece
                position = new ChessPosition(i+1,j+1);
                if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == team) {
                    teamPositions.add(position);
                }
            }
        }
        return teamPositions;
    }

    /**
     * Return a team's possible moves
     *
     * @param team the team to return all moves from
     * @param board the current board
     * @return a collection of all possible ChessPositions
     */
    public Collection<ChessMove> allMoves(TeamColor team, ChessBoard board) {
        Collection moves = new ArrayList();
        Collection<ChessPosition> teamPositions = currentTeamPositions(team, board);
        // Loop through the team's positions to get possible moves
        for(ChessPosition position : teamPositions) {
            moves.addAll(board.getPiece(position).pieceMoves(board,position));
        }
        return moves;
    }

    /**
     * Return a team's valid moves
     *
     * @param team the team to return all moves from
     * @return a collection of all possible ChessPositions
     */
    public Collection<ChessMove> allValidMoves(TeamColor team, ChessBoard board) {
        Collection moves = new ArrayList();
        Collection<ChessPosition> teamPositions = currentTeamPositions(team, board);
        // Loop through the team's positions to get possible moves
        for(ChessPosition position : teamPositions) {
            moves.addAll(validMoves(position, board));
        }
        return moves;
    }

    /**
     * Returns just the end positions of every move a certain team can make
     *
     * @param team team in question
     * @param board the board where to check positions
     * @return Collection of ChessPositions
     */
    public Collection<ChessPosition> allEndPositions(TeamColor team, ChessBoard board) {
        Collection<ChessPosition> endPositions = new ArrayList();
        Collection<ChessMove> allMoves = allMoves(team, board);
        for (ChessMove move : allMoves) {
            endPositions.add(move.getEndPosition());
        }
        return endPositions;
    }

    /**
     * Returns a teams king position
     *
     * @param teamColor which team to check for king
     * @param board the board where to look for king
     * @return returns the ChessPosition of the king
     */
    public ChessPosition findKing(TeamColor teamColor, ChessBoard board) {
        ChessPosition kingPosition = null;
        Collection<ChessPosition> teamPositions = currentTeamPositions(teamColor, board);
        for (ChessPosition position : teamPositions) {
            if (board.getPiece(position).getPieceType() == ChessPiece.PieceType.KING) {
                kingPosition = position;
            }
        }
        return kingPosition;
    }



    // METHODS

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        if (this.board.getPiece(startPosition) != null) {
            ChessGame.TeamColor teamColor = this.board.getPiece(startPosition).getTeamColor();
            // Find all moves of one piece
            Collection<ChessMove> allMoves = this.board.getPiece(startPosition).pieceMoves(board, startPosition);
            for (ChessMove move : allMoves) {
                // Clone board
                ChessBoard clonedBoard = this.board.clone();
                // Check to see if any of these moves don't cause Check
                clonedBoard.doMove(move);
                if (!isInCheck(teamColor, clonedBoard)) {
                    // Add move to validMoves
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @param board the board where we want to check the validMoves
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition, ChessBoard board) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        if (board.getPiece(startPosition) != null) {
            ChessGame.TeamColor teamColor = board.getPiece(startPosition).getTeamColor();
            // Find all moves of one piece
            Collection<ChessMove> allMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
            for (ChessMove move : allMoves) {
                // Clone board
                ChessBoard clonedBoard = board.clone();
                // Check to see if any of these moves don't cause Check
                clonedBoard.doMove(move);
                if (!isInCheck(teamColor, clonedBoard)) {
                    // Add move to validMoves
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = new ArrayList<>();

        if (board.getPiece(move.getStartPosition()) != null && board.getPiece(move.getStartPosition()).getTeamColor() == this.teamTurn) {
            validMoves = validMoves(move.getStartPosition());
        }
        else { throw new InvalidMoveException(); }
        // Check to see if move is in the validMoves Collection
        boolean validMove = false;
        if (validMoves.contains(move)) {
            validMove = true;
        }
        if (validMove) {
            // Make copy of the board
            ChessBoard cloneBoard = this.board.clone();
            // Determine piece color
            ChessGame.TeamColor teamColor = cloneBoard.getPiece(move.getStartPosition()).getTeamColor();
            // Make the move on the copy
            cloneBoard.doMove(move);
            // Check for Check/Stalemate on the copy
            if (!isInCheck(teamColor, cloneBoard) && !isInStalemate(teamColor, cloneBoard)) {
                // If valid, make move
                this.board.doMove(move);
                if (teamTurn == TeamColor.WHITE) { teamTurn = TeamColor.BLACK; }
                else { teamTurn = TeamColor.WHITE; }
            }
            else { // Else throw InvalidMoveException
                throw new InvalidMoveException();
            }
        }
        else { // Else throw InvalidMoveException
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor, this.board);
        if (kingPosition == null) { return false; }
        TeamColor otherTeam = TeamColor.BLACK;
        if (teamColor == TeamColor.BLACK) { otherTeam = TeamColor.WHITE; }
        Collection<ChessPosition> endPositions = allEndPositions(otherTeam, board);
        for (ChessPosition position : endPositions) {
            if (position.getRow() == kingPosition.getRow() && position.getColumn() == kingPosition.getColumn()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @param board the board where we want to check for inCheck
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor, ChessBoard board) {
        ChessPosition kingPosition = findKing(teamColor, board);
        if (kingPosition == null) { return false; }
        TeamColor otherTeam = TeamColor.BLACK;
        if (teamColor == TeamColor.BLACK) { otherTeam = TeamColor.WHITE; }
        Collection<ChessPosition> endPositions = allEndPositions(otherTeam, board);
        for (ChessPosition position : endPositions) {
            if (position.getRow() == kingPosition.getRow() && position.getColumn() == kingPosition.getColumn()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    // Need to check all possible moves the current team can make and if any bring king out of check
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> teamMoves = allMoves(teamColor, board);
        for (ChessMove move : teamMoves) {
            ChessBoard boardClone = board.clone();
            boardClone.doMove(move);
            if (!isInCheck(teamColor, boardClone)) { return false; }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // Find all moves for teamColor
        Collection<ChessMove> teamMoves = allValidMoves(teamColor, board);
        // Find all ValidMoves for teamColor
        // If count of all moves == 0 and !isInCheck
        if (teamMoves.size() == 0 && !isInCheck(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @param board the board where we want to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor, ChessBoard board) {
        // Find all moves for teamColor
        Collection<ChessMove> teamMoves = allValidMoves(teamColor, board);
        // Find all ValidMoves for teamColor
        // If count of all moves == 0 and !isInCheck
        if (teamMoves.size() == 0 && !isInCheck(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
        this.teamTurn = TeamColor.WHITE;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
