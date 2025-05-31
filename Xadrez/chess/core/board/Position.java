package chess.core.board;

public class Position {
    public final int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(String position) {
        this.col = position.charAt(0) - 'A';
        this.row = 8 - Character.getNumericValue(position.charAt(1));
    }

    /**
     * Retorna a posição como uma letra de A a H
     * e um número de 1 a 8.
     * Por exemplo, B6, H3, etc.
     */
    public String getPosition() {
        return "" + (char) ('A' + this.col) + (this.row + 1);
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;

        return row == position.row && col == position.col;
    }

    public final boolean equals(String string) {
        return this.getPosition().equals(string);
    }
}
