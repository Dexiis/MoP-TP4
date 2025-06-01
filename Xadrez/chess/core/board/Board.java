package chess.core.board;

import chess.core.Color;
import chess.core.Square;
import chess.core.board.pieces.Piece;
import chess.core.board.pieces.Rook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementa um tabuleiro de xadrez passivo.
 * Por passivo, significa que esta implementação não oferece validação de movimentos de peças nem qualquer validação de regras de xadrez.
 */
public class Board implements Serializable {
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

    /**
     * Obter posições das colunas - horizontal - entre duas posições.
     *
     * @param initPosition - Posição inicial. Tem de estar na mesma linha que a posição final
     * @param endPosition  - Posição final. Tem de estar na mesma linha que a posição inicial
     * @return - Lista de posições no meio.
     */
    public boolean areSquaresEmpty(Position initPosition, Position endPosition) {
        boolean result = true;

        for (Position position : getColumnPositionBetween(initPosition, endPosition))
            result = result && getSquare(position).isEmpty();
        return result;
    }

    /**
     * Retorna uma lista de todas as posições ocupadas por peças (pretas e brancas) no tabuleiro.
     *
     * @return Uma lista de objetos {@link Position} das casas ocupadas.
     */
    public List<Position> getAllPositionOccupied() {
        List<Position> allPieces = new LinkedList<>();

        allPieces.addAll(this.getAllPositionOccupied(Color.BLACK));
        allPieces.addAll(this.getAllPositionOccupied(Color.WHITE));
        return allPieces;
    }

    /**
     * Retorna uma lista de todas as posições ocupadas por peças de uma cor específica no tabuleiro.
     *
     * @param color A cor das peças a procurar (e.g., {@code Color.BLACK} ou {@code Color.WHITE}).
     * @return Uma lista de objetos {@link Position} das casas ocupadas pela cor especificada.
     */
    public List<Position> getAllPositionOccupied(Color color) {
        List<Position> allPieces = new LinkedList<>();

        for (Position position : getAllPositions())
            if (this.getPiece(position).getColor() == color) allPieces.add(position);
        return allPieces;
    }

    /**
     * Retorna uma cópia do tabuleiro de xadrez.
     * <p>
     * Uma cópia (clone) é fornecida para garantir que o tabuleiro interno não seja modificado diretamente
     * a partir do exterior.
     *
     * @return Uma cópia bidimensional (clone) do array de {@link Square} que representa o tabuleiro.
     */
    public final Square[][] getBoard() {
        return this.board.clone();
    }

    /**
     * Retorna o tabuleiro como uma lista linear de casas ({@link Square}).
     * As casas são adicionadas à lista linha a linha, da primeira à última linha.
     *
     * @return Uma {@link List} de objetos {@link Square} que representam todas as casas do tabuleiro
     * numa sequência linear.
     */
    public final List<Square> getBoardAsList() {
        List<Square> result = new LinkedList<>();
        for (byte row = 0; row < 8; row++)
            result.addAll(Arrays.asList(board[row]).subList(0, 8));
        return result;
    }

    /**
     * Retorna a lista completa de movimentos realizados no jogo até ao momento.
     *
     * @return Uma {@link List} de objetos {@link Move} que representam o histórico de todos os movimentos.
     */
    public List<Move> getHistoryMovesList() {
        return moves;
    }

    /**
     * Gera uma representação em texto do histórico de todos os movimentos realizados.
     * Cada movimento é formatado para mostrar a peça, a posição inicial e a posição final.
     *
     * @return Uma {@code String} contendo o histórico de movimentos, com cada movimento numa nova linha.
     */
    public String getHistoryMovesText() {
        StringBuilder result = new StringBuilder();
        for (Move move : moves)
            result.append(move.getPiece().toString()).append(" - From:").append(move.getInitPosition()).append(" To:").append(move.getEndPosition()).append("\n");
        return result.toString();
    }

    /**
     * Retorna o último movimento realizado no jogo.
     *
     * @return O objeto {@link Move} que representa o último movimento, ou {@code null} se não houver movimentos.
     */
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

    public void makeCastlingMove(Position initPosition, Position endPosition) {
        //Mexer o Rei
        board[endPosition.row][endPosition.col].setPiece(board[initPosition.row][initPosition.col].getPiece());
        board[initPosition.row][initPosition.col].setEmpty();
        board[endPosition.row][endPosition.col].getPiece().setHasMoved();

        // Casteling do lado do Rei
        if (board[endPosition.row][endPosition.col + 1].getPiece() instanceof Rook) {
            board[endPosition.row][endPosition.col - 1].setPiece(board[endPosition.row][endPosition.col + 1].getPiece());
            board[endPosition.row][endPosition.col - 1].getPiece().setHasMoved();
            board[endPosition.row][endPosition.col + 1].setEmpty();
        }

        // Casteling do lado da Rainha
        if (board[endPosition.row][endPosition.col - 2].getPiece() instanceof Rook) {
            board[endPosition.row][endPosition.col + 1].setPiece(board[endPosition.row][endPosition.col - 2].getPiece());
            board[endPosition.row][endPosition.col + 1].getPiece().setHasMoved();
            board[endPosition.row][endPosition.col - 2].setEmpty();
        }

        moves.add(new Move(board[endPosition.row][endPosition.col].getPiece(), initPosition, endPosition));
    }

    /**
     * Realiza um movimento de "en passant" no tabuleiro.
     * Move a peça da posição inicial para a final e remove a peça capturada "en passant".
     *
     * @param initPosition A posição inicial da peça que faz o movimento.
     * @param endPosition  A posição final da peça após o movimento.
     */
    public void makeEnPassantMove(Position initPosition, Position endPosition) {
        this.makeSimpleMove(initPosition, endPosition);

        Piece thisPiece = board[endPosition.row][endPosition.col].getPiece();
        int enPassantRow = thisPiece.isWhite() ? endPosition.row - 1 : endPosition.row + 1;
        board[enPassantRow][endPosition.col].setEmpty();
    }

    /**
     * Realiza um movimento de promoção de peão no tabuleiro.
     * A peça na posição final é substituída pela nova peça promovida.
     *
     * @param newPiece     A nova {@link Piece} (e.g., Rainha, Torre) para a qual o peão é promovido.
     * @param initPosition A posição inicial do peão antes da promoção.
     * @param endPosition  A posição final onde a nova peça será colocada.
     */
    public void makePromotionMove(Piece newPiece, Position initPosition, Position endPosition) {
        board[endPosition.row][endPosition.col].setPiece(newPiece);
        board[initPosition.row][initPosition.col].setEmpty();
    }

    /**
     * Realiza um movimento simples de uma peça no tabuleiro.
     * Move a peça da posição inicial para a final, atualiza o seu estado "hasMoved" e adiciona o movimento ao histórico.
     *
     * @param initPosition A posição inicial da peça.
     * @param endPosition  A posição final da peça.
     */
    public void makeSimpleMove(Position initPosition, Position endPosition) {
        board[endPosition.row][endPosition.col].setPiece(board[initPosition.row][initPosition.col].getPiece());
        board[initPosition.row][initPosition.col].setEmpty();
        board[endPosition.row][endPosition.col].getPiece().setHasMoved();
        moves.add(new Move(board[endPosition.row][endPosition.col].getPiece(), initPosition, endPosition));
    }

    /**
     * Reinicia o tabuleiro para a sua configuração inicial e limpa todo o histórico de movimentos.
     */
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

    private List<Position> getColumnPositionBetween(Position initPosition, Position endPosition) {
        ArrayList<Position> result = new ArrayList<>();
        int startPosition = Math.min(initPosition.col, endPosition.col);
        int finishPosition = Math.max(initPosition.col, endPosition.col);

        for (int i = startPosition + 1; i < finishPosition; i++)
            result.add(new Position(initPosition.row, i));
        return result;
    }

    private void initBoard() {
        for (byte row = 0; row < 8; row++)
            for (byte col = 0; col < 8; col++)
                board[row][col] = new Square();
    }
}
