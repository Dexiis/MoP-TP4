package Pieces;

import java.util.ArrayList;

public abstract class Pieces {
    private Color color;

    protected Pieces(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board);

    public abstract String toString();

    public void setHasMoved() {

    }
}
