package chess.core.board.pieces;

import chess.core.Color;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    public String toString() {
        return super.getColor().equals(Color.WHITE) ? "♘" : "♞";
    }
}
