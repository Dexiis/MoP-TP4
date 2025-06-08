package chess.core.board;

import chess.core.PieceColor;
import chess.core.board.pieces.*;

public class PieceFactory {
    public static Piece createPiece(Type type, PieceColor pieceColor) {
        return switch (type) {
            case KING -> new King(pieceColor);
            case PAWN ->  new Pawn(pieceColor);
            case ROOK -> new Rook(pieceColor);
            case QUEEN -> new Queen(pieceColor);
            case KNIGHT -> new Knight(pieceColor);
            case BISHOP -> new Bishop(pieceColor);
        };
    }
}