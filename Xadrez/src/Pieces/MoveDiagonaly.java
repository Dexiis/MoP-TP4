package Pieces;

import java.util.ArrayList;

public interface MoveDiagonaly {
    ArrayList<PiecePosition> availableDiagonalMoves(int currentColPosition, int currentRowPosition, Pieces[][] board);
}
