package Pieces;

public interface EnPassant {
    void availableEnPassantMoves(int currentColPosition, int currentRowPosition, Pieces[][] board);
}
