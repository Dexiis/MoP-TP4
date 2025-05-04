package Pieces;

import java.util.ArrayList;

public interface MoveStraight {
    ArrayList<PiecePosition> availableStraightMoves(int currentColPosition, int currentRowPosition, Pieces[][] board);
}
