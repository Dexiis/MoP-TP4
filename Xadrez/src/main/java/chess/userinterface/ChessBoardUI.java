package chess.userinterface;

import chess.core.*;
import chess.core.board.Position;
import chess.core.board.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoardUI extends JFrame {
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color HIGHLIGHT_COLOR = new Color(75, 150, 255);
    private static final int SQUARE_SIZE = 100;
    private final JPanel boardPanel;
    private final Map<String, String> pieceImagePaths;
    private GameManager game;
    private Piece selectedPiece = null;
    private JPanel selectedSquareUI = null;
    private int sourceIndex = -1;
    private Position sourcePosition = null;

    /**
     * Construtor da interface do tabuleiro de xadrez.
     * Inicializa a janela, o painel do tabuleiro e desenha as peças iniciais.
     *
     * @param game A instância da sua classe GameManager que contém a lógica do jogo.
     */
    public ChessBoardUI(GameManager game) {
        this.game = game;
        askToLoadGame();
        setTitle("Tabuleiro de Xadrez");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Salvando o jogo antes de sair...");
                saveBoard();
                System.exit(0);
            }
        });

        pieceImagePaths = new HashMap<>();
        initializePieceImagePaths();

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        add(boardPanel, BorderLayout.CENTER);

        drawBoard();

        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static JPanel gameOverPanel(PieceColor winner) {
        String winnerMessage;
        if (winner != null)
            winnerMessage = (winner == PieceColor.WHITE ? "As peças Brancas" : "As peças Pretas") + " venceram!";
        else winnerMessage = "O jogo terminou em empate!";

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(230, 230, 230)); // Um cinzento claro
        JLabel messageLabel = new JLabel("<html><center><br>" + winnerMessage + "<br><br>Gostaria de jogar novamente?</center></html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messagePanel.add(messageLabel);
        return messagePanel;
    }

    /**
     * Reinicia o jogo, recolocando todas as peças nas suas posições iniciais.
     * Após o reinício, o tabuleiro na "interface" gráfica é redesenhado para refletir o estado atual.
     */
    public void resetGame() {
        game.resetGame();
        drawBoard();
    }

    private void askToLoadGame() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Deseja continuar o seu último jogo?",
                "Carregar Jogo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            System.out.println("Opção: Sim. Carregando o último jogo...");
            this.game = Serialization.loadBoardGUI();
        }
    }

    private void drawBoard() {
        boardPanel.removeAll();
        List<Square> board = game.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel squareUI = new JPanel();
                squareUI.setLayout(new BorderLayout());
                squareUI.setBackground(this.getOriginalSquareColor(row, col));

                final int currentIndex = (row * 8) + col;

                final Position currentPosition;
                final Piece pieceInSquare;

                if (currentIndex < board.size()) {
                    Square currentSquare = board.get(currentIndex);
                    pieceInSquare = currentSquare.getPiece();
                    currentPosition = currentSquare.getPosition();
                } else {
                    currentPosition = new Position((char) ('A' + col), 8 - row);
                    pieceInSquare = null;
                    System.err.println("Erro: Índice do tabuleiro fora dos limites: " + currentIndex);
                }

                if (pieceInSquare != null) {
                    String pieceKey = (pieceInSquare.isWhite() ? "white" : "black") + pieceInSquare.getClass().getSimpleName();
                    String imagePath = pieceImagePaths.get(pieceKey);

                    if (imagePath != null) {
                        ImageIcon pieceIcon = loadImage(imagePath);
                        if (pieceIcon != null) {
                            JLabel pieceLabel = new JLabel(pieceIcon);
                            pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            pieceLabel.setVerticalAlignment(SwingConstants.CENTER);
                            squareUI.add(pieceLabel, BorderLayout.CENTER);
                        } else
                            System.err.println("Erro: Imagem para " + pieceKey + " não encontrada ou não pôde ser redimensionada.");
                    } else
                        System.err.println("Erro: Caminho da imagem não mapeado para " + pieceKey + ". Verifique initializePieceImagePaths().");
                }

                squareUI.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(squareUI, currentPosition, pieceInSquare, currentIndex);
                    }
                });

                boardPanel.add(squareUI);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private JPanel gameOverPanel(JDialog gameOverDialog) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(230, 230, 230));

        JButton playAgainButton = playAgainPanel(gameOverDialog);

        JButton exitButton = new JButton("Sair do Jogo");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setBackground(new Color(200, 80, 80)); // Vermelho
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(_ -> {
            resetGame();
            saveBoard();
            System.exit(0);
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        return buttonPanel;
    }

    private Color getOriginalSquareColor(int index) {
        return this.getOriginalSquareColor(index / 8, index % 8);
    }

    private Color getOriginalSquareColor(int row, int col) {
        return (row + col) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
    }

    private void handleSquareClick(JPanel clickedSquareUI, Position clickedPosition, Piece pieceAtClickedPosition, int clickedIndex) {
        if (selectedPiece == null) {
            if (pieceAtClickedPosition != null) {
                if (pieceAtClickedPosition.getColor() == game.nextPlayer()) {
                    selectedPiece = pieceAtClickedPosition;
                    selectedSquareUI = clickedSquareUI;
                    sourcePosition = clickedPosition;
                    sourceIndex = clickedIndex;
                    clickedSquareUI.setBackground(HIGHLIGHT_COLOR);
                    System.out.println("Peça selecionada: " + selectedPiece.getClass().getSimpleName() + " em " + sourcePosition);
                } else System.out.println("Não é a vez dessa cor jogar.");
            } else System.out.println("Nenhuma peça para selecionar nesta casa.");
        } else {
            if (clickedPosition.equals(sourcePosition)) {
                selectedSquareUI.setBackground(getOriginalSquareColor(sourceIndex));
                selectedPiece = null;
                selectedSquareUI = null;
                sourcePosition = null;
                sourceIndex = -1;
                System.out.println("Peça desselecionada.");
            } else {
                System.out.println("Tentando mover " + selectedPiece.getClass().getSimpleName() + " de " + sourcePosition + " para " + clickedPosition);
                game.play(selectedPiece, sourcePosition, clickedPosition);

                selectedSquareUI.setBackground(getOriginalSquareColor(sourceIndex));
                selectedPiece = null;
                selectedSquareUI = null;
                sourcePosition = null;
                sourceIndex = -1;
                drawBoard();
                System.out.println("Movimento processado. Tabuleiro atualizado.");

                if (game.isFinished()) showGameOverDialog();
            }
        }
    }

    private void initializePieceImagePaths() {
        pieceImagePaths.put("whiteKing", "images/whiteKing.png");
        pieceImagePaths.put("whiteQueen", "images/whiteQueen.png");
        pieceImagePaths.put("whiteRook", "images/whiteRook.png");
        pieceImagePaths.put("whiteBishop", "images/whiteBishop.png");
        pieceImagePaths.put("whiteKnight", "images/whiteKnight.png");
        pieceImagePaths.put("whitePawn", "images/whitePawn.png");
        pieceImagePaths.put("blackKing", "images/blackKing.png");
        pieceImagePaths.put("blackQueen", "images/blackQueen.png");
        pieceImagePaths.put("blackRook", "images/blackRook.png");
        pieceImagePaths.put("blackBishop", "images/blackBishop.png");
        pieceImagePaths.put("blackKnight", "images/blackKnight.png");
        pieceImagePaths.put("blackPawn", "images/blackPawn.png");
    }

    private ImageIcon loadImage(String path) {
        URL imageUrl = getClass().getClassLoader().getResource(path);
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(ChessBoardUI.SQUARE_SIZE, ChessBoardUI.SQUARE_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }

    private JButton playAgainPanel(JDialog gameOverDialog) {
        JButton playAgainButton = new JButton("Jogar Novamente");
        playAgainButton.setFont(new Font("Arial", Font.PLAIN, 16));
        playAgainButton.setBackground(new Color(100, 180, 100)); // Verde
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setFocusPainted(false); // Remove o contorno de foco
        playAgainButton.addActionListener(_ -> {
            gameOverDialog.dispose(); // Fecha o diálogo
            resetGame();             // Reinicia o jogo
            this.setEnabled(true);   // Reativa a janela principal
        });
        return playAgainButton;
    }

    private void saveBoard() {
        XML.create(this.game.getBoard());
        Serialization.saveBoardGUI(this.game);
    }

    private void showGameOverDialog() {
        // Desativar a janela principal para que o foco fique no diálogo
        this.setEnabled(false);

        JDialog gameOverDialog = new JDialog(this, "Fim do Jogo", true);
        gameOverDialog.setLayout(new BorderLayout(10, 10));
        gameOverDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        PieceColor winner = game.whoWon();
        JPanel messagePanel = gameOverPanel(winner);
        JPanel buttonPanel = gameOverPanel(gameOverDialog);

        gameOverDialog.add(messagePanel, BorderLayout.CENTER);
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);

        gameOverDialog.pack();
        gameOverDialog.setLocationRelativeTo(this);
        gameOverDialog.setVisible(true);
    }
}