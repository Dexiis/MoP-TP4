public class ConsoleInterface {
    private static ConsoleInterface instance = null;
    private Board board = Board.create();

    private ConsoleInterface() {
    }

    public static ConsoleInterface start() {
        if (instance == null) {
            instance = new ConsoleInterface();
        }
        return instance;
    }

    public void run() {
    }
}
