package model;
import chess.ChessGame;

public record GameData(int gameID, String winner, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
}
