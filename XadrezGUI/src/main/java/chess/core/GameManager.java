package chess.core;

import chess.core.board.Board;
import chess.core.board.Position;
import chess.core.board.RulesMaster;
import chess.core.board.Type;
import chess.core.board.pieces.Piece;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import static chess.core.board.PieceFactory.createPiece;

/**
 * Classe principal que implementa o padrão Facade
 */
public class GameManager implements Serializable {
    private final Board board;
    private final RulesMaster ruleMaster;
    private boolean isWhitePlay = true;


    public GameManager() {
        this.board = Board.getInstance();
        this.ruleMaster = new RulesMaster(board);
        this.resetGame();
    }

    private static boolean isValidPosition(String position) {
        return Pattern.compile("[A-Ha-h][1-8]").matcher(position).matches(); // Simply using a regular expression does it.
    }

    /**
     * Obtém uma lista de objetos Square que representam o tabuleiro.
     *
     * @return - Lista de objetos Square.
     */
    public List<Square> getBoard() {
        return this.board.getBoardAsList();
    }

    /**
     * Retorna uma lista de movimentos possíveis para uma dada Peça e a respetiva posição.
     *
     * @param initPosition - posição atual da peça no tabuleiro.
     * @return - Lista de Posições de movimentos válidos, ou nulo se a Peça for de um tipo desconhecido.
     */
    public List<Position> getValidMoves(Position initPosition) {
        Piece piece = this.board.getPiece(initPosition.row, initPosition.col);
        return this.ruleMaster.getValidMoves(piece, initPosition);
    }

    /**
     * Verifica se o jogo terminou ao confirmar se algum dos reis foi capturado.
     *
     * @return true se alguma das peças do rei tiver sido capturada.
     */
    public boolean isFinished() {
        return ruleMaster.isFinished();
    }

    /**
     * Obtém a próxima Cor a jogar.
     *
     * @return - Cor do próximo a jogar
     */
    public PieceColor nextPlayer() {
        return isWhitePlay ? PieceColor.WHITE : PieceColor.BLACK;
    }

    /**
     * Aceita um movimento de peça alternadamente entre a cor Branco e Preta.
     * Se for dado um movimento com a cor de peça errada, não faz nada.
     *
     * @param initPosition - posição inicial da peça, como "A2" ou "B7", etc.
     * @param endPosition  - posição final da peça, como "A2" ou "B7", etc.
     */
    public void play(String initPosition, String endPosition) {
        if (isValidPosition(initPosition) && isValidPosition(endPosition))
            this.play(new Position(initPosition.toUpperCase()), new Position(endPosition.toUpperCase()));
    }

    /**
     * Aceita um movimento de peça alternadamente entre a cor Branco e Preta.
     * Se for dado um movimento com a cor de peça errada, não faz nada.
     *
     * @param initPosition - posição inicial da peça
     * @param endPosition  - posição final da peça
     */
    public void play(Position initPosition, Position endPosition) {
        Piece piece = board.getPiece(initPosition);
        if (piece != null) this.play(piece, initPosition, endPosition);
    }

    /**
     * Aceita um movimento de peça alternadamente entre a cor Branco e Preta.
     * Se for dado um movimento com a cor de peça errada, não faz nada.
     *
     * @param piece        - Peça a mover
     * @param initPosition - posição inicial da peça
     * @param endPosition  - posição final da peça
     */
    public void play(Piece piece, Position initPosition, Position endPosition) {
        if (piece.isWhite() == this.isWhitePlay) {
            if (ruleMaster.isEnPassantMove(initPosition, endPosition))
                this.isWhitePlay = this.makeEnPassantMove(piece, initPosition, endPosition) != this.isWhitePlay;
            else if (ruleMaster.isCastlingMove(initPosition, endPosition))
                this.isWhitePlay = this.makeCastlingMove(piece, initPosition, endPosition) != this.isWhitePlay;
            else this.isWhitePlay = this.makeSimpleMove(piece, initPosition, endPosition) != this.isWhitePlay;
        }
    }

    /**
     * Jogada a ser usada APENAS para o movimento de promoção do peão.
     *
     * @param newPieceType - novo tipo de peça.
     * @param initPosition - posição inicial da peça
     * @param endPosition  - posição final da peça
     */
    public void play(Type newPieceType, String initPosition, String endPosition) {
        if (newPieceType != Type.KING) {
            Piece newPiece = createPiece(newPieceType, board.getPiece(initPosition).getColor());
            this.isWhitePlay = this.makePromotionMove(newPiece, new Position(initPosition), new Position(endPosition));
        }
    }

    /**
     * Aceita um movimento de peça alternadamente entre a cor Branco e Preta.
     * Se for dado um movimento com a cor de peça errada, não faz nada.
     *
     * @param initRow - posição da linha inicial
     * @param initCol - posição da coluna inicial
     * @param endRow  - posição da linha final
     * @param endCol  - posição da coluna final
     */
    public void play(int initRow, int initCol, int endRow, int endCol) {
        this.play(new Position(initRow, initCol), new Position(endRow, endCol));
    }

    /**
     * Reinicia o jogo, recolocando as peças.
     */
    public void resetGame() {
        this.board.resetBoard();
        this.placePieces();
        isWhitePlay = true;
    }

    /**
     * Retorna a cor vencedora se o jogo tiver terminado.
     *
     * @return A Cor do último rei em jogo ou nulo se o jogo não tiver terminado.
     */
    public PieceColor whoWon() {
        return ruleMaster.whoWon();
    }

    private List<Position> getValidMoves(Piece piece, Position position) {
        return this.ruleMaster.getValidMoves(piece, position);
    }

    private boolean makeCastlingMove(Piece piece, Position initPosition, Position endPosition) {
        if (this.getValidMoves(piece, initPosition).contains(endPosition)) {
            this.board.makeCastlingMove(initPosition, endPosition);
            return true;
        }
        return false;
    }

    private boolean makeEnPassantMove(Piece piece, Position initPosition, Position endPosition) {
        if (this.getValidMoves(piece, initPosition).contains(endPosition)) {
            this.board.makeEnPassantMove(initPosition, endPosition);
            return true;
        }
        return false;
    }

    private boolean makePromotionMove(Piece newPiece, Position initPosition, Position endPosition) {
        if (this.getValidMoves(board.getPiece(initPosition), initPosition).contains(endPosition)) {
            board.makePromotionMove(newPiece, initPosition, endPosition);
            return true;
        }
        return false;
    }

    private boolean makeSimpleMove(Piece piece, Position initPosition, Position endPosition) {
        if (this.getValidMoves(piece, initPosition).contains(endPosition)) {
            this.board.makeSimpleMove(initPosition, endPosition);
            return true;
        }
        return false;
    }

    private void placePieces() {
        // WHITE
        this.board.setPiece(createPiece(Type.ROOK, PieceColor.BLACK), 0, 0);
        this.board.setPiece(createPiece(Type.KNIGHT, PieceColor.BLACK), 0, 1);
        this.board.setPiece(createPiece(Type.BISHOP, PieceColor.BLACK), 0, 2);
        this.board.setPiece(createPiece(Type.QUEEN, PieceColor.BLACK), 0, 3);
        this.board.setPiece(createPiece(Type.KING, PieceColor.BLACK), 0, 4);
        this.board.setPiece(createPiece(Type.BISHOP, PieceColor.BLACK), 0, 5);
        this.board.setPiece(createPiece(Type.KNIGHT, PieceColor.BLACK), 0, 6);
        this.board.setPiece(createPiece(Type.ROOK, PieceColor.BLACK), 0, 7);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 0);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 1);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 2);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 3);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 4);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 5);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 6);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.BLACK), 1, 7);
        // BLACK
        this.board.setPiece(createPiece(Type.ROOK, PieceColor.WHITE), 7, 0);
        this.board.setPiece(createPiece(Type.KNIGHT, PieceColor.WHITE), 7, 1);
        this.board.setPiece(createPiece(Type.BISHOP, PieceColor.WHITE), 7, 2);
        this.board.setPiece(createPiece(Type.QUEEN, PieceColor.WHITE), 7, 3);
        this.board.setPiece(createPiece(Type.KING, PieceColor.WHITE), 7, 4);
        this.board.setPiece(createPiece(Type.BISHOP, PieceColor.WHITE), 7, 5);
        this.board.setPiece(createPiece(Type.KNIGHT, PieceColor.WHITE), 7, 6);
        this.board.setPiece(createPiece(Type.ROOK, PieceColor.WHITE), 7, 7);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 0);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 1);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 2);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 3);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 4);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 5);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 6);
        this.board.setPiece(createPiece(Type.PAWN, PieceColor.WHITE), 6, 7);
    }
}
