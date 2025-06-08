import chess.core.GameManager;
import chess.core.PieceColor;
import chess.core.Serialization;
import chess.core.XML;
import chess.userinterface.ChessBoardUI;
import chess.userinterface.ConsoleInterface;

import java.util.Scanner;

public class Main {
    private static void checkForLeavingGame(String string, ConsoleInterface console) {
        if (string.equalsIgnoreCase("QUIT")) {
            Serialization.saveBoardConsole(console);
            XML.create(console.getBoard());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // CÓDIGO DO TABULEIRO GUI
//        ChessBoardUI chessBoardUI = new ChessBoardUI(new GameManager());

        // CÓDIGO DO TABULEIRO ASCII
        ConsoleInterface console;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Deseja continuar o último jogo? (S/N)");
        String option = keyboard.nextLine();
        if (option.equalsIgnoreCase("S") && Serialization.fileExists()) console = Serialization.loadBoardConsole();
        else {
            System.out.println("Não existe nenhum jogo guardado. Um jogo novo será carregado.");
            console = new ConsoleInterface();
        }

        String initPosition, endPosition;
        boolean playing = true;

        while (playing) {
            while (!console.isFinished()) {
                console.showASCII();
                System.out.print("('quit' para sair e salvar o jogo')");
                System.out.print("\nPeça a mover para o ");
                System.out.print(console.nextPlayer() == PieceColor.BLACK ? "Preto: " : "Branco: ");
                initPosition = keyboard.nextLine();
                checkForLeavingGame(initPosition, console);
                System.out.print("\nMover para: ");
                endPosition = keyboard.nextLine();
                checkForLeavingGame(endPosition, console);
                console.play(initPosition, endPosition);
            }

            console.showASCII();
            System.out.println((console.whoWon() == PieceColor.BLACK ? "Preto " : "Branco ") + "GANHOU!!!");

            System.out.print("\nDeseja jogar novamente? (S/N)");
            if (keyboard.nextLine().equalsIgnoreCase("S")) console.resetGame();
            else playing = false;
        }
    }
}