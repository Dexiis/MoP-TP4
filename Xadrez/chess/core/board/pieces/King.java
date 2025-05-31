package chess.core.board.pieces;

import chess.core.Color;

public class King extends Piece {


    public King(Color color) {
        super(color);
    }

    public String toString() {
        return super.getColor().equals(Color.BLACK) ? "♚" : "♔";
    }
}
