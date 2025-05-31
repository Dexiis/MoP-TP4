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

        gameManager.play("G7","G6");
        gameManager.play("G2","G3");
        gameManager.play("F8","H6");
        gameManager.play("F1","H3");
        gameManager.play("G8","F6");
        gameManager.play("G1","F3");
        console.showASCII(gameManager.getBoard());
        gameManager.play("E8","G8");
        console.showASCII(gameManager.getBoard());
        gameManager.play("E1","G1");

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
