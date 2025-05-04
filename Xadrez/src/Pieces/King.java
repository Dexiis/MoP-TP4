package Pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class King extends Pieces implements Castling {
    private boolean check;
    private boolean hasMoved;

    protected King(Color color, boolean check, boolean hasMoved) {
        super(color, check, hasMoved);
        this.check = check;
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public ArrayList<PiecePosition> castlingMove(int currentColPosition, int currentRowPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();
        ArrayList<PiecePosition> castlingRightSidePositions = new ArrayList<>();
        ArrayList<PiecePosition> castlingLeftSidePositions = new ArrayList<>();
        Set<PiecePosition> threatenedPositions = new HashSet<>();
        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        Pieces rightPiece = board[currentRowPosition][currentColPosition + 3];
        Pieces leftPiece = board[currentRowPosition][currentColPosition - 4];
        boolean validMove = true;

        if (!this.check && !this.hasMoved) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Pieces targetPiece = board[col][row];
                    if (targetPiece != null && targetPiece.getColor() != currentPiece.getColor()) {
                        threatenedPositions.addAll(targetPiece.checkForAvailableMoves(col, row, board));
                    }
                }
            }

            castlingRightSidePositions.add(new PiecePosition(currentRowPosition, currentColPosition + 1));
            castlingLeftSidePositions.add(new PiecePosition(currentRowPosition, currentColPosition - 1));
            castlingRightSidePositions.add(new PiecePosition(currentRowPosition, currentColPosition + 2));
            castlingLeftSidePositions.add(new PiecePosition(currentRowPosition, currentColPosition - 2));

            if (rightPiece instanceof Rook) if (!((Rook) rightPiece).getHasMoved()) {
                for (PiecePosition position : castlingRightSidePositions) {
                    if (threatenedPositions.contains(position)) {
                        validMove = false;
                    }
                    if (validMove)
                        availablePositions.add(new PiecePosition(currentColPosition, currentRowPosition + 2));
                }
            }

            if (leftPiece instanceof Rook) if (!((Rook) leftPiece).getHasMoved()) {
                for (PiecePosition position : castlingLeftSidePositions) {
                    if (threatenedPositions.contains(position)) {
                        validMove = false;
                    }
                    if (validMove)
                        availablePositions.add(new PiecePosition(currentColPosition, currentRowPosition - 2));
                }
            }
        }

        return availablePositions;
    }

    @Override
    public ArrayList<PiecePosition> checkForAvailableMoves(int currentRowPosition, int currentColPosition, Pieces[][] board) {
        ArrayList<PiecePosition> availablePositions = new ArrayList<>();

        Pieces currentPiece = board[currentRowPosition][currentColPosition];
        int[][] kingMoves = {{-1, -1}, {-1, 0}, {-1, +1}, // Um para cima e/ou para os lados
                {0, -1}, {0, +1}, // Para os lados
                {+1, -1}, {+1, 0}, {+1, +1}, // Um para baixo e/ou para os lados
        };

        for (int[] move : kingMoves) {
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
