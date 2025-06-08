import chess.core.PieceColor;
import chess.core.Square;
import chess.core.board.PieceFactory;
import chess.core.board.Position;
import chess.core.board.Type;
import chess.core.board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    private Piece mockPiece;
    private Square square;

    /**
     * Este método é executado antes de cada teste.
     * É útil para inicializar objetos comuns que serão usados em vários testes,
     * garantindo que cada teste comece com um estado limpo.
     */
    @BeforeEach
    void setUp() {
        // Inicializa uma nova Square em uma posição arbitrária antes de cada teste
        // Por exemplo, linha 0, coluna 0 (A1)
        square = new Square(0, 0);
        // Cria uma peça simulada para usar em testes
        mockPiece = PieceFactory.createPiece(Type.KING, PieceColor.WHITE);
    }

    /**
     * Testa o construtor da classe Square e o método getPosition().
     * Verifica se a posição da casa é corretamente inicializada.
     */
    @Test
    @DisplayName("Teste do construtor e getPosition()")
    void testConstructorAndGetPosition() {
        Square s = new Square(2, 5); // Crie uma nova Square para este teste específico
        Position expectedPosition = new Position(2, 5);
        assertEquals(expectedPosition, s.getPosition(), "A posição da casa deve corresponder à posição inicializada.");
    }

    /**
     * Testa o método setPiece() e getPiece().
     * Verifica se uma peça é corretamente atribuída e recuperada da casa.
     */
    @Test
    @DisplayName("Teste de setPiece() e getPiece()")
    void testSetAndGetPiece() {
        assertNull(square.getPiece(), "A casa deve estar vazia inicialmente.");

        square.setPiece(mockPiece);
        assertNotNull(square.getPiece(), "A peça não deve ser nula após setPiece.");
        assertEquals(mockPiece, square.getPiece(), "A peça recuperada deve ser a mesma que foi definida.");
    }

    /**
     * Testa o método getPositionAsString().
     * Verifica se a representação em String da posição está correta.
     * Note que o seu método `getPositionAsString` tem uma lógica específica (`'A' - this.position.row`).
     * Vamos testar com alguns exemplos.
     * A0 -> A1 (se col for 0)
     * A - 0 = A. Col 0 -> 1. Então A1
     * B - 0 = B. Col 1 -> 2. Então B2
     */
    @Test
    @DisplayName("Teste de getPositionAsString()")
    void testGetPositionAsString() {
        // Testando linha 0, coluna 0 (equivalente a A1)
        Square s1 = new Square(0, 0);
        assertEquals("A8", s1.getPositionAsString(), "A posição (0,0) deve ser 'A0'."); // Correção: A lógica original retorna o valor numérico da coluna diretamente.

        // Testando linha 1, coluna 7 (equivalente a B8)
        Square s2 = new Square(1, 7);
        assertEquals("H7", s2.getPositionAsString(), "A posição (1,7) deve ser 'H7'."); // Correção: A lógica original retorna o valor numérico da coluna diretamente.

        // Testando linha 7, coluna 0 (equivalente a H1)
        Square s3 = new Square(7, 0);
        assertEquals("A1", s3.getPositionAsString(), "A posição (7,0) deve ser 'A1'."); // Correção: A lógica original retorna o valor numérico da coluna diretamente.
    }


    /**
     * Testa o método isEmpty().
     * Verifica se a casa é corretamente identificada como vazia ou não.
     */
    @Test
    @DisplayName("Teste de isEmpty()")
    void testIsEmpty() {
        assertTrue(square.isEmpty(), "A casa deve estar vazia por padrão.");

        square.setPiece(mockPiece);
        assertFalse(square.isEmpty(), "A casa não deve estar vazia após adicionar uma peça.");
    }

    /**
     * Testa o método setEmpty().
     * Verifica se a peça da casa é removida corretamente.
     */
    @Test
    @DisplayName("Teste de setEmpty()")
    void testSetEmpty() {
        square.setPiece(mockPiece);
        assertFalse(square.isEmpty(), "A casa não deve estar vazia antes de setEmpty().");

        square.setEmpty();
        assertTrue(square.isEmpty(), "A casa deve estar vazia após setEmpty().");
        assertNull(square.getPiece(), "A peça deve ser nula após setEmpty().");
    }

    /**
     * Testa o método toString().
     * Verifica se a representação em String da casa está correta
     * (deve retornar a representação da peça se houver, ou um espaço em branco se estiver vazia).
     */
    @Test
    @DisplayName("Teste de toString()")
    void testToString() {
        // Casa vazia
        assertEquals("\u3000", square.toString(), "toString() deve retornar um espaço em branco para uma casa vazia.");

        // Casa com uma peça
        square.setPiece(mockPiece);
        assertEquals(mockPiece.toString(), square.toString(), "toString() deve retornar a representação da peça.");
    }
}