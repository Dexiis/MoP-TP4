import Pieces.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

public class Board {
    private static Board instance = null;
    Pieces[][] board = new Pieces[8][8];

    private Board() {
    }

    public static Board create() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public void createBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = PieceFactory.createPiece(Type.PAWN, Color.BLACK, false, false);
            board[6][i] = PieceFactory.createPiece(Type.PAWN, Color.WHITE, false, false);
        }
        board[0][0] = PieceFactory.createPiece(Type.ROOK, Color.BLACK, false, false);
        board[0][7] = PieceFactory.createPiece(Type.ROOK, Color.BLACK, false, false);
        board[0][1] = PieceFactory.createPiece(Type.KNIGHT, Color.BLACK, false, false);
        board[0][6] = PieceFactory.createPiece(Type.KNIGHT, Color.BLACK, false, false);
        board[0][2] = PieceFactory.createPiece(Type.BISHOP, Color.BLACK, false, false);
        board[0][5] = PieceFactory.createPiece(Type.BISHOP, Color.BLACK, false, false);
        board[0][3] = PieceFactory.createPiece(Type.QUEEN, Color.BLACK, false, false);
        board[0][4] = PieceFactory.createPiece(Type.KING, Color.BLACK, false, false);

        board[7][0] = PieceFactory.createPiece(Type.ROOK, Color.WHITE, false, false);
        board[7][7] = PieceFactory.createPiece(Type.ROOK, Color.WHITE, false, false);
        board[7][1] = PieceFactory.createPiece(Type.KNIGHT, Color.WHITE, false, false);
        board[7][6] = PieceFactory.createPiece(Type.KNIGHT, Color.WHITE, false, false);
        board[7][2] = PieceFactory.createPiece(Type.BISHOP, Color.WHITE, false, false);
        board[7][5] = PieceFactory.createPiece(Type.BISHOP, Color.WHITE, false, false);
        board[7][3] = PieceFactory.createPiece(Type.QUEEN, Color.WHITE, false, false);
        board[7][4] = PieceFactory.createPiece(Type.KING, Color.WHITE, false, false);
    }

    private HashSet<PiecePosition> checkForAvalibleMoves(Pieces piece) {
        int currentColPosition = 0;
        int currentRowPosition = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[col][row] == piece) {
                    currentColPosition = col;
                    currentRowPosition = row;
                    break;
                }
            }
        }

        return new HashSet<>(piece.checkForAvailableMoves(currentColPosition, currentRowPosition, board));
    }

    public void printBoard() {
        System.out.print(" \u3000");
        for (char colLabel = 'a'; colLabel <= 'h'; colLabel++) {
            System.out.printf("  " + colLabel + "\u3000");
        }
        System.out.println();

        for (int row = 0; row < 8; row++) {
            System.out.print("  ");
            for (int col = 0; col < 8; col++) {
                System.out.print("＋———");
            }
            System.out.println("＋");

            int chessRowLabel = 8 - row;
            System.out.print(chessRowLabel + " ");

            for (int col = 0; col < 8; col++) {
                Pieces currentPiece = board[row][col];

                System.out.print("|");
                if (currentPiece != null) {
                    System.out.print(" " + currentPiece + " ");
                } else {System.out.print("  \u3000");}

            }
            System.out.println("|");

        }

        System.out.print("  ");
        for (int col = 0; col < 8; col++) {
            System.out.print("＋———");
        }
        System.out.println("＋");

        System.out.print(" \u3000");
        for (char colLabel = 'a'; colLabel <= 'h'; colLabel++) {
            System.out.printf("  " + colLabel + "\u3000");
        }
        System.out.println();
    }

    public void movePiece(String piecePosition, String targetedPosition) {

        if (piecePosition.length() != 2 || targetedPosition.length() != 2) {
            System.out.println("Uma das posições é inválida!");
            return;
        } else {
            int colC = 0, colT = 0;
            int rowC, rowT;
            char collC = piecePosition.charAt(0);
            char rowwC = piecePosition.charAt(1);
            char collT = targetedPosition.charAt(0);
            char rowwT = targetedPosition.charAt(1);
            Character[] colArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
            Character[] rowArray = {'1', '2', '3', '4', '5', '6', '7', '8'};
            ArrayList<Character> colList = new ArrayList<>(Arrays.asList(colArray));
            ArrayList<Character> rowList = new ArrayList<>(Arrays.asList(rowArray));

            if (colList.contains(collC) && rowList.contains(rowwC) && colList.contains(collT) && rowList.contains(rowwT)) {

                rowC = 8 - Integer.parseInt(String.valueOf(rowwC));
                switch (collC) {
                    case 'a' -> colC = 0;
                    case 'b' -> colC = 1;
                    case 'c' -> colC = 2;
                    case 'd' -> colC = 3;
                    case 'e' -> colC = 4;
                    case 'f' -> colC = 5;
                    case 'g' -> colC = 6;
                    case 'h' -> colC = 7;
                }
                rowT = Integer.parseInt(String.valueOf(rowwT)) - 1;
                switch (collT) {
                    case 'a' -> colT = 0;
                    case 'b' -> colT = 1;
                    case 'c' -> colT = 2;
                    case 'd' -> colT = 3;
                    case 'e' -> colT = 4;
                    case 'f' -> colT = 5;
                    case 'g' -> colT = 6;
                    case 'h' -> colT = 7;
                }
                PiecePosition PP = new PiecePosition(rowT, colT);
                HashSet<PiecePosition> availableMoves = checkForAvalibleMoves(board[rowC][colC]);

                if (availableMoves.contains(PP)) {
                    board[rowT][colT] = board[rowC][colC];
                    board[rowC][colC] = null;
                } else System.out.println("Jogada Impossível");
            }
        }
    }
}