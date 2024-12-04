package model;
import chess.ChessGame;

public record GameData(int gameID, boolean active, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
}
