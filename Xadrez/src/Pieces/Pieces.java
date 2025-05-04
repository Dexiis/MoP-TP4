package Pieces;

public abstract class Pieces {
    private String position;
    private Color color;

    protected Pieces(Color color, String position) {
        this.position = position;
    }

    protected Pieces(Color color, String position, boolean hasMoved) {
        this.position = position;
    }

    protected Pieces(Color color, String position, boolean check, boolean hasMoved) {
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

    public void setColor(Color color) {
        this.color = color;
    }
}
