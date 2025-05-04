import Pieces.*;

import java.util.HashSet;
import java.util.Set;

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

    public void checkForAvalibleMoves(Pieces piece) {
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

        Set<PiecePosition> availableMoves = new HashSet<>(piece.checkForAvailableMoves(currentColPosition, currentRowPosition, board));
    }
}