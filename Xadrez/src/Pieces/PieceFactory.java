package Pieces;

public class PieceFactory {

    public PieceFactory() {
    }

    public static Pieces createPiece(Type type, Color color, boolean check, boolean hasMoved) {

        switch (type) {
            case PAWN:
                return new Pawn(color, hasMoved); // Adicionar cor
            case KNIGHT:
                return new Knight(color);
            case BISHOP:
                return new Bishop(color);
            case ROOK:
                return new Rook(color, hasMoved);
            case QUEEN:
                return new Queen(color);
            case KING:
                return new King(color, check, hasMoved);
            default:
                throw new IllegalArgumentException("Tipo de peça desconhecido: " + type);
        }
    }
}