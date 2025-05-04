package Pieces;

import java.util.ArrayList;

public class Pawn extends Pieces implements EnPassant {
    Pawn(Color color, boolean hasMoved) {
        super(color, hasMoved);
    }

    @Override
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {

    }
}
