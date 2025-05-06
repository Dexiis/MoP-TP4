package Pieces;

import java.util.ArrayList;

public class Pawn extends Pieces implements EnPassant {
    private boolean hasMoved;

    Pawn(Color color, boolean hasMoved) {
        super(color);
        this.hasMoved = hasMoved;
    }

    @Override
    public void setHasMoved() {
        hasMoved = true;
    }

    @Override
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] whitePawnCaptureMoves = {{-1, -1}, {-1, +1}, // Ambas diagonais
        };
        int[][] whitePawnMoves = {{-1, 0} // Baixo
        };

        int[][] blackPawnCaptureMoves = {{+1, -1}, {+1, +1}, // Ambas diagonais
        };
        int[][] blackPawnMoves = {{+1, 0} // Cima
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

            for (int step = 1; step <= 2; step++) {
                int targetRow = currentRowPosition - step;

                if (targetRow >= 0 && targetRow < 8) {
                    Pieces targetPiece = board[targetRow][currentColPosition];
                    if (!this.hasMoved && targetPiece == null) {
                        availablePositions.add(new PiecePosition(currentRowPosition - step, currentColPosition));
                    } else if (targetPiece == null) {
                        availablePositions.add(new PiecePosition(currentRowPosition - 1, currentColPosition));
                    }
                }
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

            for (int step = 1; step <= 2; step++) {
                int targetRow = currentRowPosition + step;

                if (targetRow >= 0 && targetRow < 8) {
                    Pieces targetPiece = board[targetRow][currentColPosition];
                    if (!this.hasMoved && targetPiece == null) {
                        availablePositions.add(new PiecePosition(currentRowPosition + step, currentColPosition));
                    } else if (targetPiece == null) {
                        availablePositions.add(new PiecePosition(currentRowPosition + 1, currentColPosition));
                    }
                }
            }
        }

        return availablePositions;
    }

    @Override
    public void availableEnPassantMoves(int currentColPosition, int currentRowPosition, Pieces[][] board) {

    }

    public String toString() {
        return super.getColor().equals(Color.WHITE) ? "♙" : "♟";
    }
}
