package Pieces;

import java.util.ArrayList;

public interface Castling {
    ArrayList<PiecePosition> castlingMove(int currentColPosition, int currentRowPosition, Pieces[][] board);
}
