package chess.core.board.pieces;

import chess.core.Color;

import java.io.Serializable;

public class Knight extends Piece implements Serializable {
    public Knight(Color color) {
        super(color);
    }

    public String toString() {
        return super.getColor().equals(Color.BLACK) ? "♞" : "♘";
    }
}
