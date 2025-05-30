package chess.userinterface;

import chess.core.board.Square;

import java.util.List;

public class ConsoleInterface {
    public ConsoleInterface() {
    }

    /**
     * Obtém uma representação Unicode do tabuleiro.
     */

    public void showASCII(List<Square> board) {
        StringBuilder result = new StringBuilder();

        result.append(" ");
        for (char col = 'A'; col <= 'H'; col++) result.append("\u3000\u3000\u3000\u3000\u3000").append(col);

        result.append("\n    ╔════════╤════════╤════════╤════════╤════════╤════════╤════════╤════════╗\n");

        for (int row = 0; row < 8; row++) { // Rows
            result.append(8 - row).append("   ║");
            for (int col = 0; col < 8; col++) {
                int itemNumber = (((row*8)) + (col));
                result.append("\u3000\u3000").append(board.get(itemNumber)).append("\u3000\u3000");
                if (col < 7) result.append("│");
            }
            result.append("║   ").append(8 - row).append("\n");
            if (row < 7) result.append("    ╟────────┼────────┼────────┼────────┼────────┼────────┼────────┼────────╢\n");
        }

        result.append("    ╚════════╧════════╧════════╧════════╧════════╧════════╧════════╧════════╝\n");

        result.append(" ");
        for (char col = 'A'; col <= 'H'; col++) result.append("\u3000\u3000\u3000   ").append(col);

        result.append("\n");
        System.out.println(result);
    }
}
