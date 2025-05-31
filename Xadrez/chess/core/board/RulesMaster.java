package chess.core.board;

import chess.core.Color;
import chess.core.Square;
import chess.core.board.pieces.*;

import java.util.LinkedList;
import java.util.List;

/**
 * A classe RulesMaster contém todas as regras do jogo.
 */
public class RulesMaster {

    private final Board board;

    /**
     * Implementa um padrão de injeção de dependência
     * da classe Board.
     *
     * @param board classe do Tabuleiro
     */
    public RulesMaster(Board board) {
        this.board = board;
    }

    private static boolean isValidPosition(int row, int col) {
        final byte BOARD_SIZE = 8; // If different, won't be chess. :-)
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    /**
     * Cria uma lista de todos os movimentos que comprometeriam uma posição para uma dada cor.
     * Por exemplo, quais são os movimentos que comprometeriam uma peça Preta|Branca em A5?
     *
     * @param position - posição a analisar.
     * @param color    - cor a analisar.
     * @return - lista de movimentos de peças que comprometeriam.
     */
    public List<Move> compromisingMoves(Position position, Color color) {
        List<Move> compromiseMoves = new LinkedList<>();

        for (Position occupiedPosition : this.board.getAllPositionOccupied(color))
            if (getValidCaptureMoves(occupiedPosition).contains(position))
                compromiseMoves.add(new Move(this.board.getPiece(occupiedPosition), occupiedPosition, position));
        return compromiseMoves;
    }

    /**
     * Retorna uma lista de movimentos possíveis para uma dada Peça e a respetiva posição.
     *
     * @param position - posição atual da peça no tabuleiro.
     * @return uma Lista de Posições de movimentos válidos, ou nulo se a Peça for de um tipo desconhecido.
     */
    public List<Position> getValidMoves(Position position) {
        return getValidMoves(this.board.getPiece(position), position);
    }

    /**
     * Retorna uma lista de movimentos possíveis para uma dada **Peça** e a respetiva posição.
     *
     * @param piece    - peça a analisar
     * @param position - posição atual da peça no tabuleiro.
     * @return uma Lista de Posições de movimentos válidos, ou nulo se a Peça for de um tipo desconhecido.
     */
    public List<Position> getValidMoves(Piece piece, Position position) {
        return switch (piece) {
            case Pawn _ -> pawnValidMoves(position);
            case Rook _ -> rookValidMoves(position);
            case Knight _ -> knightValidMoves(position);
            case Bishop _ -> bishopValidMoves(position);
            case Queen _ -> queenValidMoves(position);
            case King _ -> kingValidMoves(position);
            case null, default -> new LinkedList<>(); // Ou outra ação apropriada
        };
    }

    /**
     * Verifica se este é um movimento especial de peão "en passant".
     * Regras:
     * 1 - o peão inimigo avançou duas casas na jogada anterior;
     * 2 - o peão que captura ataca a casa sobre a qual o peão inimigo passou.
     *
     * @param initPosition - Posição inicial do Peão
     * @param endPosition  - Posição final do Peão
     * @return - retorna verdadeiro se for um movimento "en passant" válido
     */
    public boolean isEnPassantMove(Position initPosition, Position endPosition) {
        Move move = board.getLastMove();
        if (move != null && move.getPiece() instanceof Pawn && board.getPiece(initPosition) instanceof Pawn)
            if (Math.abs(move.getEndPosition().row - move.getInitPosition().row) == 2)
                if (board.getSquare(endPosition).isEmpty())
                    return !move.getPiece().getColor().equals(board.getPiece(initPosition).getColor());
        return false;
    }

    public boolean isCastlingMove(Position initPosition, Position endPosition) {
        Piece piece = board.getPiece(initPosition);
        if (piece instanceof King) {
            if (!piece.hasMoved()) {
                // KING SIDE CASTLING
                Piece targetPieceKingSide = (piece.getColor() == Color.WHITE) ? board.getPiece(7, 7) : board.getPiece(0, 7);
                Position[] targetPositionsKingSide = (piece.getColor() == Color.WHITE) ? new Position[]{new Position(7, 5), new Position(7, 6)} : new Position[]{new Position(0, 5), new Position(0, 6)};

                if (targetPieceKingSide instanceof Rook && board.getSquare(targetPositionsKingSide[0]).isEmpty() && board.getSquare(targetPositionsKingSide[1]).isEmpty() && !targetPieceKingSide.hasMoved() && (endPosition.equals("H1") || endPosition.equals("H8"))) {
                    return true;
                }

                // QUEEN SIDE CASTLING
                Piece targetPieceQueenSide = (piece.getColor() == Color.WHITE) ? board.getPiece(7, 0) : board.getPiece(0, 0);
                Position[] targetPositionsQueenSide = (piece.getColor() == Color.WHITE) ? new Position[]{new Position(7, 3), new Position(7, 2), new Position(7, 1)} : new Position[]{new Position(0, 3), new Position(0, 2), new Position(0, 1)};

                if (targetPieceQueenSide instanceof Rook)
                    if (board.getSquare(targetPositionsQueenSide[0]).isEmpty() && board.getSquare(targetPositionsQueenSide[1]).isEmpty())
                        if (!targetPieceQueenSide.hasMoved())
                            return endPosition.equals("C1") || endPosition.equals("C8");
            }
        }
        return false;
    }

    /**
     * Verifica se o jogo terminou ao confirmar se algum dos reis foi capturado.
     *
     * @return true se alguma das peças do rei tiver sido capturada.
     */
    public boolean isFinished() {
        return !isKingAlive(Color.BLACK) || !isKingAlive(Color.WHITE);
    }

    /**
     * Verifica se todas as condições estão presentes para ser um movimento de promoção.
     * 1 - A peça atual deve ser um Peão
     * 2 - A posição do Peão deve estar na respetiva linha de promoção
     * 3 - A nova peça deve ser da mesma cor da peça atual.
     *
     * @param piece    - peça a substituir
     * @param position - peça a ser substituída
     * @return - verdadeiro se for um movimento de promoção válido
     */
    public boolean isPromotionMove(Piece piece, Position position) {
        return piece instanceof Pawn && position.row == (piece.isWhite() ? 0 : 7);
    }

    /**
     * Retorna a cor vencedora se o jogo tiver terminado.
     *
     * @return A Cor do último rei em jogo ou nulo se o jogo não tiver terminado.
     */
    public Color whoWon() {
        if (isFinished()) return isKingAlive(Color.WHITE) ? Color.WHITE : Color.BLACK;
        return null;
    }

    private List<Position> bishopValidMoves(Position position) {
        int row = position.row;
        int col = position.col;

        // Direções: diagonais
        // (deltaLinha, deltaColuna)
        int[][] directions = {{-1, -1}, // Diagonal superior esquerda
                {-1, 1},  // Diagonal superior direita
                {1, -1},  // Diagonal inferior esquerda
                {1, 1}    // Diagonal inferior direita
        };
        return linearMovement(directions, row, col);
    }

    private List<Position> getValidCaptureMoves(Position position) {
        return this.board.getPiece(position) instanceof Pawn ? pawnValidCaptureMovement(position) : getValidMoves(position);
    }

    private boolean isKingAlive(Color color) {
        for (Square square : this.board.getBoardAsList())
            if ((square.getPiece() instanceof King) && (square.getPiece().getColor() == color)) return true;
        return false;
    }

    private List<Position> kingValidMoves(Position position) {
        List<Position> moves = new LinkedList<>();
        int row = position.row;
        int col = position.col;

        // Direções: uma casa em qualquer direção (8 direções)
        // (deltaLinha, deltaColuna)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1},   // Horizontal e Vertical
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonais
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValidPosition(newRow, newCol)) {
                Piece targetPiece = board.getPiece(newRow, newCol);
                if (targetPiece == null)// Casa vazia
                    moves.add(new Position(newRow, newCol));
                else if (targetPiece.isWhite() != board.getPiece(row, col).isWhite()) // Peça inimiga
                    moves.add(new Position(newRow, newCol)); // Captura
                // Não pode mover para casa com peça amiga (implicitamente não adicionado)
            }
        }

        Piece king = board.getPiece(position);
        if (!king.hasMoved()) {

            // KING SIDE CASTLING
            Piece targetRookKingSide = (king.getColor() == Color.WHITE) ? board.getPiece(7, 7) : board.getPiece(0, 7);
            Position[] targetPositionsKingSide = (king.getColor() == Color.WHITE) ? new Position[]{new Position(7, 5), new Position(7, 6)} : new Position[]{new Position(0, 5), new Position(0, 6)};
            Position kingSideCastlingTarget = (king.getColor() == Color.WHITE) ? new Position(7, 6) : new Position(0, 6);

            if (targetRookKingSide instanceof Rook && board.getSquare(targetPositionsKingSide[0]).isEmpty() && board.getSquare(targetPositionsKingSide[1]).isEmpty() && !targetRookKingSide.hasMoved()) {
                moves.add(kingSideCastlingTarget);
            }

            // QUEEN SIDE CASTLING
            Piece targetQueenQueenSide = (king.getColor() == Color.WHITE) ? board.getPiece(7, 0) : board.getPiece(0, 0);
            Position[] targetPositionsQueenSide = (king.getColor() == Color.WHITE) ? new Position[]{new Position(7, 3), new Position(7, 2), new Position(7, 1)} : new Position[]{new Position(0, 3), new Position(0, 2), new Position(0, 1)};
            Position queenSideCastlingTarget = (king.getColor() == Color.WHITE) ? new Position(7, 2) : new Position(0, 2);

            if (targetQueenQueenSide instanceof Rook && board.getSquare(targetPositionsQueenSide[0]).isEmpty() && board.getSquare(targetPositionsQueenSide[1]).isEmpty() && !targetQueenQueenSide.hasMoved()) {
                moves.add(queenSideCastlingTarget);
            }
        }

        return moves;
    }

    private List<Position> knightValidMoves(Position position) {
        int row = position.row;
        int col = position.col;
        List<Position> moves = new LinkedList<>();
        Piece thisPiece = board.getPiece(row, col);

        // Estes são os 8 movimentos possíveis do cavalo em termos de (linha, coluna)
        int[][] directions = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValidPosition(newRow, newCol)) {
                Square nextSquare = board.getSquare(newRow, newCol);

                if (nextSquare.isEmpty()) // Casa vazia
                    moves.add(new Position(newRow, newCol));
                else if (nextSquare.getPiece().isWhite() != thisPiece.isWhite()) // Peça inimiga
                    moves.add(new Position(newRow, newCol)); // Captura
                // Não pode mover para casa com peça amiga (implicitamente não adicionado)
            }
        }
        return moves;
    }

    private List<Position> linearMovement(int[][] directions, int row, int col) {
        List<Position> moves = new LinkedList<>();
        Piece thisPiece = board.getPiece(row, col);
        for (int[] dir : directions) {
            int dRow = dir[0];
            int dCol = dir[1];

            for (int i = 1; i < 8; i++) { // Iterar ao longo da direção
                int newRow = row + dRow * i;
                int newCol = col + dCol * i;

                if (isValidPosition(newRow, newCol)) {
                    Square nextSquare = board.getSquare(newRow, newCol);
                    if (nextSquare.isEmpty()) {  // Casa vazia
                        moves.add(new Position(newRow, newCol));
                        continue; // Passa para a próxima casa.
                    } else if (thisPiece.isWhite() != nextSquare.getPiece().isWhite()) // Peça inimiga
                        moves.add(new Position(newRow, newCol)); // Movimento de captura
                    break;  // Encontrou uma peça (amiga ou inimiga), não pode mover mais nesta direção
                }
            }
        }
        return moves;
    }

    private List<Position> pawnValidCaptureMovement(Position position) {
        int row = position.row;
        int col = position.col;
        Piece thisPiece = board.getPiece(row, col);
        List<Position> moves = new LinkedList<>();
        int direction = thisPiece.isWhite() ? -1 : 1;    // Direção do movimento do peão ao longo das linhas (-1 para brancas, +1 para pretas)
        int[][] captureMovements = thisPiece.isWhite() ? new int[][]{{1, 1}, {1, -1}} : new int[][]{{-1, 1}, {-1, -1}}; // Movimento de captura para o En Passant


        // 1. Capturas Diagonais
        for (int[] captureMovement : captureMovements) {
            int captureRow = row + captureMovement[0];
            int captureCol = col + captureMovement[1];

            if (isValidPosition(captureRow, captureCol)) {
                Piece targetPiece = this.board.getPiece(captureRow, captureCol);
                // Para capturar, deve haver uma peça inimiga na diagonal
                if (targetPiece != null && targetPiece.isWhite() != thisPiece.isWhite())
                    moves.add(new Position(captureRow, captureCol));
            }
        }

        // 2. Movimento "En Passant"
        Position left = new Position(position.row + direction, position.col - 1);
        Position right = new Position(position.row + direction, position.col + 1);
        if (isValidPosition(left.row, left.col) && isEnPassantMove(position, left)) moves.add(left);

        if (isValidPosition(right.row, right.col) && isEnPassantMove(position, right)) moves.add(right);

        return moves;
    }

    private List<Position> pawnValidMoves(Position position) {
        int row = position.row;
        int col = position.col;
        Piece thisPiece = board.getPiece(row, col);
        List<Position> positions = new LinkedList<>();

        int direction = thisPiece.isWhite() ? -1 : 1;    // Direção do movimento do peão ao longo das linhas (-1 para brancas, +1 para pretas)
        int startRow = thisPiece.isWhite() ? 6 : 1;     // Linha inicial para peões desta cor


        // 1. Movimento Simples para a Frente (uma casa)
        int oneStepForwardRow = row + direction;
        if (isValidPosition(oneStepForwardRow, col))
            if (board.getSquare(oneStepForwardRow, col).isEmpty()) // Casa da frente deve estar vazia
                positions.add(new Position(oneStepForwardRow, col));


        // 2. Movimento Duplo para a Frente (duas casas, a partir da posição inicial)
        // Só pode mover duas casas se a primeira já estiver livre e o peão estiver na sua casa inicial.
        if (row == startRow) {
            int twoStepsForwardRow = row + (2 * direction);
            if (isValidPosition(twoStepsForwardRow, col) && board.getSquare(twoStepsForwardRow, row).isEmpty())
                positions.add(new Position(twoStepsForwardRow, col));
        }

        // Adiciona também os movimentos de captura - o peão é diferente das outras peças
        positions.addAll(pawnValidCaptureMovement(position));
        return positions;
    }

    private List<Position> queenValidMoves(Position position) {
        List<Position> moves = new LinkedList<>();

        // A rainha também se move como uma torre
        moves.addAll(rookValidMoves(position));

        // A rainha também se move como um bispo
        moves.addAll(bishopValidMoves(position));

        return moves;
    }

    private List<Position> rookValidMoves(Position position) {
        int row = position.row;
        int col = position.col;

        // Direções: horizontal e vertical
        // (linha, coluna)
        int[][] directions = {{-1, 0}, // Cima
                {1, 0},  // Baixo
                {0, -1}, // Esquerda
                {0, 1}   // Direita
        };
        return linearMovement(directions, row, col);
    }
}