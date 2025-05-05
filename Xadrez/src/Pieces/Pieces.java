package Pieces;

import java.util.ArrayList;

public abstract class Pieces {
    private String position;
    private Color color;

    protected Pieces(Color color) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public abstract ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board);
}
