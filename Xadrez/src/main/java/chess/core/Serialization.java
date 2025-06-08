package chess.core;

import chess.userinterface.ChessBoardUI;
import chess.userinterface.ConsoleInterface;

import java.io.*;

public class Serialization {
    /**
     * Salva o objeto Board atualmente em memória para um ficheiro serialization (.bin).
     */
    public static void saveBoardConsole(ConsoleInterface console) {
        try (FileOutputStream streamFile = new FileOutputStream("savedFiles/latestChessGameConsole.bin"); ObjectOutputStream objectFile = new ObjectOutputStream(streamFile)) {
            objectFile.writeObject(console);
            objectFile.flush();
            objectFile.close();
            System.out.println("Tabuleiro salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o tabuleiro!");
        }
    }

    /**
     * Salva o objeto Board atualmente em memória para um ficheiro serialization (.bin).
     */
    public static void saveBoardGUI(GameManager game) {
        try (FileOutputStream streamFile = new FileOutputStream("savedFiles/latestChessGameGUI.bin"); ObjectOutputStream objectFile = new ObjectOutputStream(streamFile)) {
            objectFile.writeObject(game);
            objectFile.flush();
            objectFile.close();
            System.out.println("Tabuleiro salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o tabuleiro!");
        }
    }

    /**
     * Carrega um objeto Board a partir de um ficheiro serializado (.bin).
     *
     * @return {@code false} se o carregamento for bem-sucedido, {@code true} se ocorrer um erro.
     */
    public static ConsoleInterface loadBoardConsole() {
        try (FileInputStream streamFile = new FileInputStream("savedFiles/latestChessGameConsole.bin"); ObjectInputStream objectFile = new ObjectInputStream(streamFile)) {
            return (ConsoleInterface) objectFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o tabuleiro");
            return new ConsoleInterface();
        }
    }

    /**
     * Carrega um objeto Board a partir de um ficheiro serializado (.bin).
     *
     * @return {@code false} se o carregamento for bem-sucedido, {@code true} se ocorrer um erro.
     */
    public static GameManager loadBoardGUI() {
        try (FileInputStream streamFile = new FileInputStream("savedFiles/latestChessGameGUI.bin"); ObjectInputStream objectFile = new ObjectInputStream(streamFile)) {
            return (GameManager) objectFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o tabuleiro");
            return new GameManager();
        }
    }

    /**
     * Verifica se existe um ficheiro de jogo salvo.
     * Esta função apenas verifica a existência do ficheiro no sistema de ficheiros,
     * não a sua validade ou conteúdo.
     *
     * @return {@code true} se o ficheiro "latestChessGame.bin" existir; {@code false} caso contrário.
     */
    public static boolean fileExists() {
        File file = new File("savedFiles/latestChessGame.bin");
        return file.exists() && file.isFile(); // Verifica se existe e se é um ficheiro (não uma pasta)
    }
}
