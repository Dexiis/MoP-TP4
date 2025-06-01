package chess.core.board;

import chess.core.board.pieces.Piece;

import java.io.Serializable;

public class Move implements Serializable {
    private final Piece piece;
    private final Position initPosition;
    private final Position endPosition;


    public Move(Piece piece, Position initPosition, Position endPosition) {
        this.piece = piece;
        this.initPosition = initPosition;
        this.endPosition = endPosition;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getInitPosition() {
        return initPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    @Override
    public String toString() {
        return piece + " from " + initPosition + " to " + endPosition;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Move move)) return false;

        return piece.equals(move.piece) && initPosition.equals(move.initPosition) && endPosition.equals(move.endPosition);
    }

    @Override
    public int hashCode() {
        int result = piece.hashCode();
        result = 31 * result + initPosition.hashCode();
        result = 31 * result + endPosition.hashCode();
        return result;
    }
}
