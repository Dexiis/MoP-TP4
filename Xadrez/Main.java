import chess.core.Color;
import chess.userinterface.ConsoleInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleInterface console = new ConsoleInterface();
        Scanner play = new Scanner(System.in);
        String initPosition, endPosition;

        while (!console.isFinished()) {
            console.showASCII();
            System.out.print("Piece to move for ");
            System.out.print(console.nextPlayer() == Color.BLACK ? "Black:" : "White:");
            initPosition = play.nextLine();
            System.out.print("Move to: ");
            endPosition = play.nextLine();
            console.play(initPosition, endPosition);
        }

        System.out.println((console.whoWon() == Color.BLACK ? "Black " : "White ") + "WON!!!");
        console.showASCII();
    }
}
