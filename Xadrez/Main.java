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

        //KING SIDE CASTLING DUMMY MOVES
//        gameManager.play("G2","G3");
//        gameManager.play("G7","G6");
//        gameManager.play("F1","H3");
//        gameManager.play("F8","H6");
//        gameManager.play("G1","F3");
//        gameManager.play("G8","F6");
//        console.showASCII(gameManager.getBoard());
//        gameManager.play("E1","G1");
//        console.showASCII(gameManager.getBoard());
//        gameManager.play("E8","G8");

        //QUEEN SIDE CASTLING DUMMY MOVES
        gameManager.play("B2","B3");
        gameManager.play("B7","B6");
        gameManager.play("C1","A3");
        gameManager.play("C8","A6");
        gameManager.play("B1","C3");
        gameManager.play("B8","C6");
        gameManager.play("D2","D3");
        gameManager.play("D7","D6");
        gameManager.play("D1","D2");
        gameManager.play("D8","D7");
        console.showASCII(gameManager.getBoard());
        gameManager.play("E1","C1");
        console.showASCII(gameManager.getBoard());
        gameManager.play("E8","C8");

        while (!gameManager.isFinished()) {
            console.showASCII(gameManager.getBoard());
            System.out.print("Piece to move for ");
            System.out.print(gameManager.nextPlayer() == Color.BLACK ? "Black:" : "White:");
            initPosition = play.nextLine();
            System.out.print("Move to: ");
            endPosition = play.nextLine();
            gameManager.play(initPosition, endPosition);
        }

        System.out.println((gameManager.whoWon() == Color.BLACK ? "Black " : "White ") + "WON!!!");
        console.showASCII(gameManager.getBoard());
    }
}
