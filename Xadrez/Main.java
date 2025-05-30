import chess.core.Color;
import chess.core.GameManager;
import chess.userinterface.ConsoleInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        ConsoleInterface console = new ConsoleInterface();
        Scanner play = new Scanner(System.in);
        String initPosition, endPosition;

        while (!gameManager.isFinished()) {
            console.showASCII(gameManager.getBoard());
            System.out.print("Piece to move for ");
            System.out.print(gameManager.nextPlayer() == Color.WHITE ? "White:" : "Black:");
            initPosition = play.nextLine();
            System.out.print("Move to: ");
            endPosition = play.nextLine();
            gameManager.play(initPosition, endPosition);
        }

        System.out.println((gameManager.whoWon() == Color.WHITE ? "White " : "Black ") + "WON!!!");
        console.showASCII(gameManager.getBoard());
    }
}
