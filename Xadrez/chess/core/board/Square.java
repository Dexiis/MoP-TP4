package chess.core.board;

import chess.core.board.pieces.Piece;

public class Square {
    private Piece piece;

    public Square() {

    }

    public Square(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return this.piece == null;
    }

    public void setEmpty() {
        this.piece = null;
    }

    private String printSquare() {
        return this.piece != null ? this.piece.toString() : "\u3000";
    }

    public String toString() {
        return printSquare();
    }
}
