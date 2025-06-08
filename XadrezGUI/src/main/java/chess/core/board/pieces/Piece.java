package chess.core.board.pieces;

import chess.core.PieceColor;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    private final PieceColor pieceColor;
    private boolean hasMoved = false;

    protected Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public final PieceColor getColor() {
        return this.pieceColor;
    }

    public final boolean hasNotMoved() {
        return !this.hasMoved;
    }

    public final boolean isWhite() {
        return this.pieceColor == PieceColor.WHITE;
    }

    public final boolean isBlack() {
        return this.pieceColor == PieceColor.BLACK;
    }

    public final void setHasMoved() {
        this.hasMoved = true;
    }

    public abstract String toString();

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Piece piece)) return false;

        return hasMoved == piece.hasMoved && pieceColor == piece.pieceColor;
    }

    @Override
    public int hashCode() {
        int result = pieceColor.hashCode();
        result = 31 * result + Boolean.hashCode(hasMoved);
        return result;
    }
}