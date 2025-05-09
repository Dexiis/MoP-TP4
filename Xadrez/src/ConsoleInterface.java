import Pieces.Color;

import java.util.Scanner;

public class ConsoleInterface {
    private static ConsoleInterface instance = null;
    private Board board = Board.create();
    private String piece;
    private String targetedPosition;
    Scanner input = new Scanner(System.in);

    private ConsoleInterface() {
    }

    public static ConsoleInterface start() {
        if (instance == null) {
            instance = new ConsoleInterface();
        }
        return instance;
    }

    public void run() {
        int finished = 4; //'4' equivale a que o jogo ainda não acabou
        board.createBoard();
        Color[] cores = {Color.BLACK, Color.WHITE};
        while (finished == 4) {
            for (int index = 0; index <= 1; index++) {
                board.printBoard();
                System.out.println("Escolha a posição da peça que deseja mover: ");
                piece = input.nextLine();
                board.possibleMoves(piece);
                System.out.println("Escolha o lugar para onde a deseja mover: ");
                targetedPosition = input.nextLine();
                board.movePiece(piece, targetedPosition, cores[index]);
                finished = board.checkForFinished(cores[1 - index]);
            }
        }
    }
}
