package Pieces;

import java.util.ArrayList;

public class Bishop extends Pieces implements MoveDiagonaly {

    protected Bishop(Color color) {
        super(color);
    }

    @Override
    public ArrayList<PiecePosition> availableDiagonalMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] diagonalDirections = {
                {-1, -1}, // Cima-Esquerda
                {-1, +1}, // Cima-Direita
                {+1, -1}, // Baixo-Esquerda
                {+1, +1}  // Baixo-Direita
        };

        for (int[] direction : diagonalDirections) {
            int rowDirection = direction[0];
            int colDirection = direction[1];

            for (int step = 1; step < 8; step++) {

                int targetRow = currentRowPosition + rowDirection * step;
                int targetCol = currentColPosition + colDirection * step;

                if (targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8) {
                    Pieces targetPiece = board[targetRow][targetCol];

                    if (targetPiece == null) {
                        availablePositions.add(new PiecePosition(targetRow, targetCol));
                    } else {
                        if (targetPiece.getColor() != currentPiece.getColor()) {
                            availablePositions.add(new PiecePosition(targetRow, targetCol));
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return availablePositions;
    }

    @Override
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        return availableDiagonalMoves(currentColPosition, currentRowPosition, board);
    }
}
