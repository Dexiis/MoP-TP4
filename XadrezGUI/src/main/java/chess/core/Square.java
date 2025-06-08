package chess.core;

import chess.core.board.Position;
import chess.core.board.pieces.Piece;

import java.io.Serializable;

public class Square implements Serializable {
    private final Position position;
    private Piece piece;

    public Square(int row, int col) {
        this.position = new Position(row, col);
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Position getPosition() {
        return this.position;
    }

    /**
     * Retorna a posição desta casa no formato de uma ‘String’ do tipo
     * A2, H1 ou E7, etc.
     *
     * @return {@code String} da posição da casa.
     */
    public String getPositionAsString() {
        return String.valueOf((char) ('A' + this.position.col)) + (8 - this.position.row);
    }

    /**
     * Verifica se esta casa do tabuleiro está vazia.
     * Uma casa é considerada vazia se não contiver nenhuma peça.
     *
     * @return {@code true} se a casa não contiver uma peça (ou seja, {@code piece} é nulo),
     * {@code false} caso contrário.
     */
    public boolean isEmpty() {
        return this.piece == null;
    }

    /**
     * Define esta casa do tabuleiro como vazia.
     * Isso é feito ao remover qualquer peça que esteja atualmente nesta casa, definindo-a como nula.
     */
    public void setEmpty() {
        this.piece = null;
    }

    private String printSquare() {
        return this.piece != null ? this.piece.toString() : "\u3000";
    }

    public String toString() {
        return printSquare();
    }
}
