package chess.core.board;

import java.io.Serializable;

public class Position implements Serializable {
    public final int row;
    public final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(String position) {
        this.col = position.charAt(0) - 'A';
        this.row = 8 - Character.getNumericValue(position.charAt(1));
    }

    public final boolean equals(String string) {
        return this.getPosition().equals(string);
    }

    /**
     * Retorna a posição à esquerda da posição dáda.
     *
     * @return - posição à esquerda (coluna - 1) ou null se coluna-1 < 0
     */
    public Position getLeftPosition() {
        return col - 1 < 0 ? null : new Position(row, col - 1);
    }

    /**
     * Retorna a posição como uma letra de "A" a "H"
     * e um número de 1 a 8.
     * Por exemplo, B6, H3, etc.
     */
    public String getPosition() {
        return "" + (char) ('A' + this.col) + (8 - this.row);
    }

    /**
     * Retorna a posição à esquerda da posição dáda.
     *
     * @return - posição à esquerda (coluna - 1) ou null se coluna-1 < 0
     */
    public Position getRightPosition() {
        return col + 1 > 7 ? null : new Position(row, col + 1);
    }

    /**
     * Retorna true se a peça dáda está acima da peça dáda.
     *
     * @param position posição para comparar.
     * @return true se esta peça está acima da peça dáda.
     */
    public boolean isAboveOf(Position position) {
        return this.row < position.row;
    }

    /**
     * Retorna true se a peça dáda está abaixo da peça dáda.
     *
     * @param position posição para comparar.
     * @return true se esta peça está abaixo da peça dáda.
     */
    public boolean isBelowOf(Position position) {
        return this.row > position.row;
    }

    /**
     * Retorna true se a posição dáda está à esquerda da posição.
     *
     * @param position posição para comparar.
     * @return true se esta posição está à esquerda da peça posição.
     */
    public boolean isOnLeftOf(Position position) {
        return this.col < position.col;
    }

    /**
     * Retorna true se a peça posição está à direita da posição.
     *
     * @param position posição para comparar.
     * @return true se esta posição está à direita da peça posição.
     */
    public boolean isOnRightOf(Position position) {
        return this.col > position.col;
    }

    /**
     * Retorna true se a peça posição está ao lado da posição.
     *
     * @param position posição para comparar.
     * @return true se esta posição dáda está ao lado da posição.
     */
    public boolean isSideBySideOf(Position position) {
        return isOnRightOf(position) || isOnLeftOf(position);
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

    public String toString() {
        return getPosition();
    }
}
