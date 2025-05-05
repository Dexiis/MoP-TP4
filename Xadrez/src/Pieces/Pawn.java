package Pieces;

import java.util.ArrayList;

public class Pawn extends Pieces implements EnPassant {
    private boolean hasMoved;

    Pawn(Color color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    @Override
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] whitePawnCaptureMoves = {{-1, -1}, {-1, +1}, // Ambas diagonais
        };

        int[][] blackPawnCaptureMoves = {{+1, -1}, {+1, +1}, // Ambas diagonais
        };

        if (this.getColor() == Color.WHITE) {
            for (int[] move : whitePawnCaptureMoves) {
                int rowOffset = move[0];
                int colOffset = move[1];

                int targetRow = currentRowPosition + rowOffset;
                int targetCol = currentColPosition + colOffset;

                if (targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8) {
                    Pieces targetPiece = board[targetRow][targetCol];

                    if (targetPiece != null && targetPiece.getColor() != currentPiece.getColor()) {
                        availablePositions.add(new PiecePosition(targetRow, targetCol));
                    }
                }
            }

            if (!this.hasMoved) {
                availablePositions.add(new PiecePosition(currentRowPosition - 1, currentColPosition));
                availablePositions.add(new PiecePosition(currentRowPosition - 2, currentColPosition));
            } else if (currentRowPosition - 1 >= 0) {
                availablePositions.add(new PiecePosition(currentRowPosition - 1, currentColPosition));
            }

        } else {
            for (int[] move : blackPawnCaptureMoves) {
                int rowOffset = move[0];
                int colOffset = move[1];

                int targetRow = currentRowPosition + rowOffset;
                int targetCol = currentColPosition + colOffset;

                if (targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8) {
                    Pieces targetPiece = board[targetRow][targetCol];

                    if (targetPiece != null && targetPiece.getColor() != currentPiece.getColor()) {
                        availablePositions.add(new PiecePosition(targetRow, targetCol));
                    }
                }
            }

            if (!this.hasMoved) {
                availablePositions.add(new PiecePosition(currentRowPosition + 1, currentColPosition));
                availablePositions.add(new PiecePosition(currentRowPosition + 2, currentColPosition));
            } else if (currentRowPosition + 1 < 8) {
                availablePositions.add(new PiecePosition(currentRowPosition + 1, currentColPosition));
            }
        }

        return availablePositions;
    }

    @Override
    public void availableEnPassantMoves(int currentColPosition, int currentRowPosition, Pieces[][] board) {

    }
}
