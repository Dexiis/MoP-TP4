package chess.core.board.pieces;

import chess.core.Color;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    public String toString() {
        return super.getColor().equals(Color.BLACK) ? "♜" : "♖";
    }
}
