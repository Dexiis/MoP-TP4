package chess.core.board.pieces;

import chess.core.Color;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    private final Color color;
    private boolean hasMoved = false;

    protected Piece(Color color) {
        this.color = color;
    }

    public final Color getColor() {
        return this.color;
    }

    public final boolean hasMoved() {
        return this.hasMoved;
    }

    public final boolean isWhite() {
        return this.color == Color.WHITE;
    }

    public final boolean isBlack() {
        return this.color == Color.BLACK;
    }

    public final void setHasMoved() {
        this.hasMoved = true;
    }

    public abstract String toString();

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Piece piece)) return false;

        return hasMoved == piece.hasMoved && color == piece.color;
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + Boolean.hashCode(hasMoved);
        return result;
    }
}