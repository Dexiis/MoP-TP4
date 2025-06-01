package chess.userinterface;

import chess.core.Color;
import chess.core.GameManager;
import chess.core.Square;
import chess.core.board.Position;
import chess.core.board.pieces.Piece;

import java.util.List;

public class ConsoleInterface {
    final GameManager gameManager = new GameManager();

    public ConsoleInterface() {
    }

    /**
     * Verifica se o jogo terminou ao confirmar se algum dos reis foi capturado.
     *
     * @return true se alguma das peças do rei tiver sido capturada.
     */
    public boolean isFinished() {
        return gameManager.isFinished();
    }

    /**
     * Obtém a próxima Cor a jogar.
     *
     * @return - Cor do próximo a jogar
     */
    public Color nextPlayer() {
        return gameManager.nextPlayer();
    }

    /**
     * Aceita um movimento de peça alternadamente entre a cor Branca e Preta.
     * Se for dado um movimento com a cor de peça errada, não faz nada.
     *
     * @param piece        - Peça a mover
     * @param initPosition - posição inicial da peça
     * @param endPosition  - posição final da peça
     */
    public void play(Piece piece, Position initPosition, Position endPosition) {
        gameManager.play(piece, initPosition, endPosition);
    }

    /**
     * Aceita um movimento de peça alternadamente entre a cor Branca e Preta.
     * Se for dado um movimento com a cor de peça errada, não faz nada.
     *
     * @param initPosition - posição inicial da peça, como "A2" ou "B7", etc.
     * @param endPosition  - posição final da peça, como "A2" ou "B7", etc.
     */
    public void play(String initPosition, String endPosition) {
        gameManager.play(initPosition, endPosition);
    }

    /**
     * Reinicia o jogo, recolocando as peças.
     */
    public void resetGame() {
        gameManager.resetGame();
    }

    /**
     * Obtém uma representação Unicode do tabuleiro.
     */
    // DEPENDENDO DO MONITOR, O TABULEIRO PODE OU NÃO FICAR CORRETAMENTE ALINHADO NOS IDE's
    // A MAIOR PARTE DAS CONSOLAS NÃO SÃO CAPAZES DE LER 'UNICODE' QUE FOI USADA NA REPRESENTAÇÃO VISUAL DO TABULEIRO
    public void showASCII() {
        List<Square> board = gameManager.getBoard();
        StringBuilder result = new StringBuilder();

        result.append(" ");
        for (char col = 'A'; col <= 'H'; col++) result.append("\u3000\u3000\u3000\u3000\u3000").append(col);

        result.append("\n    ╔════════╤════════╤════════╤════════╤════════╤════════╤════════╤════════╗\n");

        for (int row = 0; row < 8; row++) {
            result.append(8 - row).append("   ║");
            for (int col = 0; col < 8; col++) {
                int itemNumber = (((row * 8)) + (col));
                result.append("\u3000\u3000").append(board.get(itemNumber)).append("\u3000\u3000");
                if (col < 7) result.append("│");
            }
            result.append("║   ").append(8 - row).append("\n");
            if (row < 7)
                result.append("    ╟────────┼────────┼────────┼────────┼────────┼────────┼────────┼────────╢\n");
        }

        result.append("    ╚════════╧════════╧════════╧════════╧════════╧════════╧════════╧════════╝\n");

        result.append(" ");
        for (char col = 'A'; col <= 'H'; col++) result.append("\u3000\u3000\u3000   ").append(col);

        result.append("\n");
        System.out.println(result);
    }

    /**
     * Retorna a cor vencedora se o jogo tiver terminado.
     *
     * @return A Cor do último rei em jogo ou nulo se o jogo não tiver terminado.
     */
    public Color whoWon() {
        return gameManager.whoWon();
    }
}
