package chess.core.board.pieces;

import chess.core.Color;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    public String toString() {
        return super.getColor().equals(Color.WHITE) ? "♗" : "♝";
    }
}
