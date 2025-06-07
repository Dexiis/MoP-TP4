package chess.core;

import chess.core.board.Position;
import chess.core.board.pieces.Piece;

import java.io.Serializable;

public class Square implements Serializable {
    private Piece piece;
    private Position position;

    public Square() {

    }

    public Square(int row, int col) {
        this.position = new Position(row, col);
    }

    public Square(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String getPositionAsString() {
        return "" + ('A' - this.position.row) + this.position.col;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public void setPosition(int row, int col) {
        this.position = new Position(row, col);
    }

    private String printSquare() {
        return this.piece != null ? this.piece.toString() : "\u3000";
    }

    public String toString() {
        return printSquare();
    }
}
