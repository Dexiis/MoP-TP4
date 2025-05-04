public class Board {
    private static Board instance = null;

    private Board() {
    }

    public static Board create() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }
}