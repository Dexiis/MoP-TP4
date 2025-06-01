import chess.core.Color;
import chess.core.Serialization;
import chess.core.XML;
import chess.userinterface.ConsoleInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleInterface console = new ConsoleInterface();
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Deseja continuar o último jogo? (S/N)");
        String option = keyboard.nextLine();
        if (option.equalsIgnoreCase("S")) {
            if (Serialization.fileExists()) {
                console = Serialization.loadBoard();
            } else {
                System.out.println("Não existe nenhum jogo guardado. Um jogo novo será carregado.");
            }
        }

        Scanner play = new Scanner(System.in);
        String initPosition, endPosition;
        boolean playing = true;

        while (playing) {
            while (!console.isFinished()) {
                console.showASCII();
                System.out.print("('quit' para sair e salvar o jogo')");
                System.out.print("\nPeça a mover para o ");
                System.out.print(console.nextPlayer() == Color.BLACK ? "Preto: " : "Branco: ");
                initPosition = play.nextLine();
                checkForLeavingGame(initPosition, console);
                System.out.print("\nMover para: ");
                endPosition = play.nextLine();
                checkForLeavingGame(endPosition, console);
                console.play(initPosition, endPosition);
            }

            console.showASCII();
            System.out.println((console.whoWon() == Color.BLACK ? "Preto " : "Branco ") + "GANHOU!!!");

            System.out.print("\nDeseja jogar novamente? (S/N)");
            if (keyboard.nextLine().equalsIgnoreCase("S")) {console.resetGame();}
            else playing = false;
        }
    }

    private static void checkForLeavingGame(String string, ConsoleInterface console) {
        if (string.equalsIgnoreCase("QUIT")) {
            Serialization.saveBoard(console);
            XML.create(console.getBoard());

            System.exit(0);
        }
    }
}
