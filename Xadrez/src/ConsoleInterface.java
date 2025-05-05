import java.util.Scanner;

public class ConsoleInterface {
    private static ConsoleInterface instance = null;
    private Board board = Board.create();
    private boolean finished = false;
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
        board.createBoard();
        while (!finished) {
            board.printBoard();
            System.out.println("Escolha a posição da peça que deseja mover: ");
            piece = input.nextLine();
            System.out.println("Escolha o lugar para onde a deseja mover: ");
            targetedPosition = input.nextLine();
            board.movePiece(piece,targetedPosition);
        }
    }
}
