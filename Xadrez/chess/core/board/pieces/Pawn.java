package chess.core.board.pieces;

import chess.core.Color;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }


    public String toString() {
        return super.getColor().equals(Color.WHITE) ? "♙" : "♟";
    }
}
