package Pieces;

public class Rook extends Pieces implements MoveStraight, Castling {
    private boolean hasMoved;

    protected Rook(Color color, String position, boolean hasMoved) {
        super(color, position, hasMoved);
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public void horizontalMove() {

    }

    @Override
    public void verticalMove() {

    }
}
