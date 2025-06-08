import chess.core.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes JUnit para a classe Position.
 * Garante que os construtores e métodos da classe Position funcionam como esperado.
 */
class PositionTest {

    // --- Testes para Construtores ---

    @Test
    @DisplayName("Teste do construtor com linha e coluna (int, int)")
    void testPositionIntIntConstructor() {
        // Teste de uma posição válida
        Position pos = new Position(0, 0); // A1
        assertEquals(0, pos.row, "A linha deve ser 0 para A1");
        assertEquals(0, pos.col, "A coluna deve ser 0 para A1");

        // Teste de outra posição válida
        Position pos2 = new Position(7, 7); // H1
        assertEquals(7, pos2.row, "A linha deve ser 7 para H1");
        assertEquals(7, pos2.col, "A coluna deve ser 7 para H1");
    }

    @Test
    @DisplayName("Teste do construtor com string (ex: 'A1', 'H8')")
    void testPositionStringConstructor() {
        // Teste de uma posição típica (A1)
        Position posA1 = new Position("A1");
        assertEquals(7, posA1.row, "Para 'A1', a linha deve ser 7");
        assertEquals(0, posA1.col, "Para 'A1', a coluna deve ser 0");

        // Teste de outra posição típica (H8)
        Position posH8 = new Position("H8");
        assertEquals(0, posH8.row, "Para 'H8', a linha deve ser 0");
        assertEquals(7, posH8.col, "Para 'H8', a coluna deve ser 7");

        // Teste de uma posição no meio (D4)
        Position posD4 = new Position("D4");
        assertEquals(4, posD4.row, "Para 'D4', a linha deve ser 4");
        assertEquals(3, posD4.col, "Para 'D4', a coluna deve ser 3");
    }

    // --- Testes para equals ---

    @Test
    @DisplayName("Teste do método equals(String)")
    void testEqualsString() {
        Position pos = new Position(0, 0); // Corresponde a "A8"
        assertTrue(pos.equals("A8"), "A posição (0,0) deve ser igual a 'A8'");
        assertFalse(pos.equals("B8"), "A posição (0,0) não deve ser igual a 'B8'");
        assertFalse(pos.equals("A7"), "A posição (0,0) não deve ser igual a 'A7'");
    }

    @Test
    @DisplayName("Teste do método equals(Object)")
    void testEqualsObject() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(0, 0);
        Position pos3 = new Position(1, 0);
        Position pos4 = new Position(0, 1);

        // Objetos iguais
        assertEquals(pos1, pos2, "Duas posições com a mesma linha e coluna devem ser iguais");

        // Objeto nulo
        assertFalse(pos1.equals(null), "Um objeto não deve ser igual a nulo");

        // Tipo diferente
        assertEquals(new Position("A8"), pos1, "Um objeto Position deve ser igual a uma String correta");

        // Linha diferente
        assertNotEquals(pos1, pos3, "Posições com linhas diferentes não devem ser iguais");

        // Coluna diferente
        assertNotEquals(pos1, pos4, "Posições com colunas diferentes não devem ser iguais");
    }

    // --- Testes para getLeftPosition e getRightPosition ---

    @Test
    @DisplayName("Teste do método getLeftPosition")
    void testGetLeftPosition() {
        Position pos = new Position(4, 4); // E4
        Position leftPos = pos.getLeftPosition(); // D4
        assertNotNull(leftPos, "A posição à esquerda não deve ser nula");
        assertEquals(4, leftPos.row, "A linha da posição à esquerda deve ser a mesma");
        assertEquals(3, leftPos.col, "A coluna da posição à esquerda deve ser col - 1");

        // Teste na borda esquerda (coluna 0)
        Position farLeftPos = new Position(4, 0); // A4
        assertNull(farLeftPos.getLeftPosition(), "A posição à esquerda de A4 deve ser nula");
    }

    @Test
    @DisplayName("Teste do método getRightPosition")
    void testGetRightPosition() {
        Position pos = new Position(4, 4); // E4
        Position rightPos = pos.getRightPosition(); // F4
        assertNotNull(rightPos, "A posição à direita não deve ser nula");
        assertEquals(4, rightPos.row, "A linha da posição à direita deve ser a mesma");
        assertEquals(5, rightPos.col, "A coluna da posição à direita deve ser col + 1");

        // Teste na borda direita (coluna 7)
        Position farRightPos = new Position(4, 7); // H4
        assertNull(farRightPos.getRightPosition(), "A posição à direita de H4 deve ser nula");
    }

    // --- Testes para getPosition ---

    @Test
    @DisplayName("Teste do método getPosition (formato String)")
    void testGetPosition() {
        Position posA1 = new Position(7, 0); // Linha 7, Coluna 0 ⇾ A1
        assertEquals("A1", posA1.getPosition(), "A posição (7,0) deve retornar 'A1'");

        Position posH8 = new Position(0, 7); // Linha 0, Coluna 7 ⇾ H8
        assertEquals("H8", posH8.getPosition(), "A posição (0,7) deve retornar 'H8'");

        Position posD4 = new Position(4, 3); // Linha 4, Coluna 3 ⇾ D4
        assertEquals("D4", posD4.getPosition(), "A posição (4,3) deve retornar 'D4'");
    }

    // --- Testes para isAboveOf, isBelowOf, isOnLeftOf, isOnRightOf, isSideBySideOf ---

    @Test
    @DisplayName("Teste do método isAboveOf")
    void testIsAboveOf() {
        Position pos1 = new Position(4, 4); // E4
        Position pos2 = new Position(5, 4); // E3 (abaixo de E4)
        Position pos3 = new Position(3, 4); // E5 (acima de E4)

        assertTrue(pos3.isAboveOf(pos1), "E5 deve estar acima de E4");
        assertFalse(pos1.isAboveOf(pos1), "E4 não deve estar acima de E4");
        assertTrue(pos1.isAboveOf(pos2), "E4 deve estar acima de E3");
    }

    @Test
    @DisplayName("Teste do método isBelowOf")
    void testIsBelowOf() {
        Position pos1 = new Position(4, 4); // E4
        Position pos2 = new Position(5, 4); // E3 (abaixo de E4)
        Position pos3 = new Position(3, 4); // E5 (acima de E4)

        assertTrue(pos2.isBelowOf(pos1), "E3 deve estar abaixo de E4");
        assertFalse(pos1.isBelowOf(pos1), "E4 não deve estar abaixo de E4");
        assertTrue(pos1.isBelowOf(pos3), "E4 deve estar abaixo de E5");
    }

    @Test
    @DisplayName("Teste do método isOnLeftOf")
    void testIsOnLeftOf() {
        Position pos1 = new Position(4, 4); // E4
        Position pos2 = new Position(4, 5); // F4 (à direita de E4)
        Position pos3 = new Position(4, 3); // D4 (à esquerda de E4)

        assertTrue(pos3.isOnLeftOf(pos1), "D4 deve estar à esquerda de E4");
        assertFalse(pos1.isOnLeftOf(pos1), "E4 não deve estar à esquerda de E4");
        assertTrue(pos1.isOnLeftOf(pos2), "E4 deve estar à esquerda de F4");
    }

    @Test
    @DisplayName("Teste do método isOnRightOf")
    void testIsOnRightOf() {
        Position pos1 = new Position(4, 4); // E4
        Position pos2 = new Position(4, 5); // F4 (à direita de E4)
        Position pos3 = new Position(4, 3); // D4 (à esquerda de E4)

        assertTrue(pos2.isOnRightOf(pos1), "F4 deve estar à direita de E4");
        assertFalse(pos1.isOnRightOf(pos1), "E4 não deve estar à direita de E4");
        assertTrue(pos1.isOnRightOf(pos3), "E4 deve estar à direita de D4");
    }

    @Test
    @DisplayName("Teste do método isSideBySideOf")
    void testIsSideBySideOf() {
        Position pos1 = new Position(4, 4); // E4
        Position posLeft = new Position(4, 3); // D4
        Position posRight = new Position(4, 5); // F4
        Position posAbove = new Position(3, 4); // E5
        Position posBelow = new Position(5, 4); // E3
        Position posSame = new Position(4, 4); // E4

        assertTrue(pos1.isSideBySideOf(posLeft), "E4 deve estar lado a lado com D4");
        assertTrue(pos1.isSideBySideOf(posRight), "E4 deve estar lado a lado com F4");
        assertFalse(pos1.isSideBySideOf(posAbove), "E4 não deve estar lado a lado com E5 (está acima)");
        assertFalse(pos1.isSideBySideOf(posBelow), "E4 não deve estar lado a lado com E3 (está abaixo)");
        assertFalse(pos1.isSideBySideOf(posSame), "E4 não deve estar lado a lado com E4 (é a mesma posição)");
    }

    // --- Testes para hashCode e toString ---

    @Test
    @DisplayName("Teste do método hashCode")
    void testHashCode() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(0, 0);
        Position pos3 = new Position(1, 0);

        // Objetos iguais devem ter o mesmo hashCode
        assertEquals(pos1.hashCode(), pos2.hashCode(), "Hash codes devem ser iguais para objetos iguais");

        // Objetos diferentes devem ter hash codes diferentes (geralmente, mas não garantido)
        assertNotEquals(pos1.hashCode(), pos3.hashCode(), "Hash codes devem ser diferentes para objetos diferentes");
    }

    @Test
    @DisplayName("Teste do método toString")
    void testToString() {
        Position pos = new Position(7, 0); // A1
        assertEquals("A1", pos.toString(), "toString deve retornar 'A1'");

        Position pos2 = new Position(0, 7); // H8
        assertEquals("H8", pos2.toString(), "toString deve retornar 'H8'");
    }
}
