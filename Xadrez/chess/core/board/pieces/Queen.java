package chess.core.board.pieces;

import chess.core.Color;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    public String toString() {
        return super.getColor().equals(Color.BLACK) ? "♛" : "♕";
    }
}
