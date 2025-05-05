package Pieces;

import java.util.ArrayList;

public class Knight extends Pieces {
    protected Knight(Color color) {
        super(color);
    }

    @Override
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] knightMoves = {{-2, -1}, {-2, +1}, // Dois para cima, um para os lados
                {-1, -2}, {-1, +2}, // Um para cima, dois para os lados
                {+1, -2}, {+1, +2}, // Um para baixo, dois para os lados
                {+2, -1}, {+2, +1}  // Dois para baixo, um para os lados
        };

        for (int[] move : knightMoves) {
            int rowOffset = move[0];
            int colOffset = move[1];

            int targetRow = currentRowPosition + rowOffset;
            int targetCol = currentColPosition + colOffset;

            if (targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8) {
                Pieces targetPiece = board[targetRow][targetCol];

                if (targetPiece == null || targetPiece.getColor() != currentPiece.getColor()) {
                    availablePositions.add(new PiecePosition(targetRow, targetCol));
                }
            }
        }

        return availablePositions;
    }
}
