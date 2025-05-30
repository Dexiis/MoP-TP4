package chess.core.board;

import chess.core.Color;
import chess.core.board.pieces.*;

public class PieceFactory {
    public static Piece createPiece(Type type, Color color) {
        return switch (type) {
            case KING -> new King(color);
            case PAWN ->  new Pawn(color);
            case ROOK -> new Rook(color);
            case QUEEN -> new Queen(color);
            case KNIGHT -> new Knight(color);
            case BISHOP -> new Bishop(color);
        };
    }
}