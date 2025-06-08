package chess.core.board.pieces;

import chess.core.PieceColor;

import java.io.Serializable;

public class Knight extends Piece implements Serializable {
    public Knight(PieceColor pieceColor) {
        super(pieceColor);
    }

    public String toString() {
        return super.getColor().equals(PieceColor.BLACK) ? "♞" : "♘";
    }
}
