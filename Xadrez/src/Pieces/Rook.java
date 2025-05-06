package Pieces;

import java.util.ArrayList;

public class Rook extends Pieces implements MoveStraight {
    private boolean hasMoved;

    protected Rook(Color color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    @Override
    public void setHasMoved() {
        hasMoved = true;
    }

    @Override
    public ArrayList<PiecePosition> availableStraightMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] straightDirections = {{-1, 0}, // Para cima
                {+1, 0}, // Para baixo
                {0, -1}, // Para a esquerda
                {0, +1}  // Para a direita
        };

        for (int[] direction : straightDirections) {
            int rowDirection = direction[0];
            int colDirection = direction[1];

            for (int step = 1; step < 8; step++) { // Verifica até 7 casas de distância

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
        return availableStraightMoves(currentColPosition, currentRowPosition, board);
    }

    public String toString() {
        return super.getColor().equals(Color.WHITE) ? "♖" : "♜";
    }
}
