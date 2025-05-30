package chess.core.board;

import chess.core.Color;
import chess.core.board.pieces.Piece;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements a passive chess board.
 * By passive, means that this implementation doesn't provide piece move validation or any chess rule validation.
 */
public class Board {
    static Board instance = null;
    private final Square[][] board = new Square[8][8];
    private final List<Move> moves = new LinkedList<>();

    private Board() {
        initBoard();
    }

    public static Board getInstance() {
        if (instance == null) instance = new Board();
        return instance;
    }

    private static Position translatePosition(String position) {
        int col = position.charAt(0) - 'A';
        int row = 8 - Character.getNumericValue(position.charAt(1));
        return new Position(row, col);
    }

    public List<Position> getAllPositionOccupied() {
        List<Position> allPieces = new LinkedList<>();

        allPieces.addAll(this.getAllPositionOccupied(Color.WHITE));
        allPieces.addAll(this.getAllPositionOccupied(Color.BLACK));
        return allPieces;
    }

    public List<Position> getAllPositionOccupied(Color color) {
        List<Position> allPieces = new LinkedList<>();

        for (Position position : getAllPositions())
            if (this.getPiece(position).getColor() == color) allPieces.add(position);
        return allPieces;
    }

    public final Square[][] getBoard() {
        return this.board.clone();
    }

    public final List<Square> getBoardAsList() {
        List<Square> result = new LinkedList<>();
        for (byte row = 0; row < 8; row++)
            result.addAll(Arrays.asList(board[row]).subList(0, 8));
        return result;
    }

    public List<Move> getHistoryMovesList() {
        return moves;
    }

    public String getHistoryMovesText() {
        StringBuilder result = new StringBuilder();
        for (Move move : moves)
            result.append(move.getPiece().toString()).append(" - From:").append(move.getInitPosition()).append(" To:").append(move.getEndPosition()).append("\n");
        return result.toString();
    }

    public Move getLastMove() {
        return !moves.isEmpty() ? moves.getLast() : null;
    }

    public Piece getPiece(int row, int col) {
        return this.getSquare(row, col).getPiece();
    }

    public Piece getPiece(String position) {
        return this.getSquare(position).getPiece();
    }

    public Piece getPiece(Position position) {
        return this.getSquare(position.row, position.col).getPiece();
    }

    public Square getSquare(int row, int col) {
        return this.board[row][col];
    }

    public Square getSquare(String position) {
        return getSquare(translatePosition(position).row, translatePosition(position).col);
    }

    public Square getSquare(Position position) {
        return getSquare(position.row, position.col);
    }

    public void makeEnPassantMove(Position initPosition, Position endPosition) {
        this.makeSimpleMove(initPosition, endPosition);

        Piece thisPiece = board[endPosition.row][endPosition.col].getPiece();
        int enPassantRow = thisPiece.isWhite() ? endPosition.row - 1 : endPosition.row + 1;
        board[enPassantRow][endPosition.col].setEmpty();
    }

    public void makePromotionMove(Piece newPiece, Position initPosition, Position endPosition) {
        board[endPosition.row][endPosition.col].setPiece(newPiece);
        board[initPosition.row][initPosition.col].setEmpty();
    }

    public void makeSimpleMove(Position initPosition, Position endPosition) {
        board[endPosition.row][endPosition.col].setPiece(board[initPosition.row][initPosition.col].getPiece()); // Don't care to tell the piece is captured, just remove it.
        board[initPosition.row][initPosition.col].setEmpty();
        board[endPosition.row][endPosition.col].getPiece().setHasMoved();
        moves.add(new Move(board[endPosition.row][endPosition.col].getPiece(), initPosition, endPosition));
    }

    public void resetBoard() {
        this.initBoard();
        this.moves.clear();
    }

    public void setPiece(Piece piece, String position) {
        this.setPiece(piece, translatePosition(position));
    }

    public void setPiece(Piece piece, Position position) {
        this.setPiece(piece, position.row, position.col);
    }

    public void setPiece(Piece piece, int row, int col) {
        this.board[row][col].setPiece(piece);
    }

    private List<Position> getAllPositions() {
        List<Position> allPositions = new LinkedList<>();

        for (byte row = 0; row < 8; row++)
            for (byte col = 0; col < 8; col++)
                allPositions.add(new Position(row, col));
        return allPositions;
    }

    private void initBoard() {
        for (byte row = 0; row < 8; row++)
            for (byte col = 0; col < 8; col++)
                board[row][col] = new Square();
    }
}
