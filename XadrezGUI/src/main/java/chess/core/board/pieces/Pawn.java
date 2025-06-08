package chess.core.board.pieces;

import chess.core.PieceColor;

import java.io.Serializable;

public class Pawn extends Piece implements Serializable {

    public Pawn(PieceColor pieceColor) {
        super(pieceColor);
    }


    public String toString() {
        return super.getColor().equals(PieceColor.BLACK) ? "♟" : "♙";
    }
}
