package Pieces;

import java.util.ArrayList;

public class Queen extends Pieces implements MoveDiagonaly, MoveStraight {
    protected Queen(Color color) {
        super(color);
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
    public ArrayList<PiecePosition> availableDiagonalMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] diagonalDirections = {{-1, -1}, // Cima-Esquerda
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
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentColPosition, int currentRowPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();
        availablePositions.addAll(availableDiagonalMoves(currentColPosition, currentRowPosition, board));
        availablePositions.addAll(availableStraightMoves(currentColPosition, currentRowPosition, board));

        return availablePositions;
    }

    public String toString() {
        return super.getColor().equals(Color.WHITE) ? "♕" : "♛";
    }
}
