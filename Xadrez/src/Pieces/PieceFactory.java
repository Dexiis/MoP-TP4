package Pieces;

public class PieceFactory {

    public PieceFactory() {
    }

    public Pieces createPiece(Type type, Color color, String position, boolean check, boolean hasMoved) {

        switch (type) {
            case PAWN:
                return new Pawn(color, position); // Adicionar cor
            case KNIGHT:
                return new Knight(color, position);
            case BISHOP:
                return new Bishop(color, position);
            case ROOK:
                return new Rook(color, position, hasMoved);
            case QUEEN:
                return new Queen(color, position);
            case KING:
                return new King(color, position, check, hasMoved);
            default:
                throw new IllegalArgumentException("Tipo de peça desconhecido: " + type);
        }
    }

    // Se precisares de uma peça sem estado inicial (como hasMoved),
    // podes ter outro método ou um valor padrão para hasMoved no construtor da peça.
    // public Pieces createPiece(TipoPeca type, Cor color, String position) {
    //     return createPiece(type, color, position, false); // hasMoved padrão como false
    // }
}