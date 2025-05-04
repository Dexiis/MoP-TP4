package Pieces;

public class King extends Pieces implements Castling {
    private boolean check;
    private boolean hasMoved;

    protected King(Color color, String position, boolean check, boolean hasMoved) {
        super(color, position, check, hasMoved);
        this.check = check;
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
