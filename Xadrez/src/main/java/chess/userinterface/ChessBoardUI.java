package chess.userinterface;

import chess.core.GameManager;
import chess.core.PieceColor;
import chess.core.Square;
import chess.core.board.Position;
import chess.core.board.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoardUI extends JFrame {
    private GameManager game;
    private JPanel boardPanel;

    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color HIGHLIGHT_COLOR = new Color(75, 150, 255);

    private static final int SQUARE_SIZE = 100;

    private final Map<String, String> pieceImagePaths;

    private Piece selectedPiece = null;
    private JPanel selectedSquareUI = null;
    private Position sourcePosition = null;
    private int sourceIndex = -1;

    /**
     * Construtor da interface do tabuleiro de xadrez.
     * Inicializa a janela, o painel do tabuleiro e desenha as peças iniciais.
     *
     * @param game A instância da sua classe GameManager que contém a lógica do jogo.
     */
    public ChessBoardUI(GameManager game) {
        this.game = game;
        setTitle("Tabuleiro de Xadrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

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

    /**
     * Retorna o tabuleiro de xadrez como uma lista de casas.
     *
     * @return Uma lista de objetos {@link Square} do tabuleiro.
     */
    public List<Square> getBoard() {
        return game.getBoard();
    }

    /**
     * Verifica se o jogo terminou ao confirmar se algum dos reis foi capturado.
     *
     * @return true se alguma das peças do rei tiver sido capturada.
     */
    public boolean isFinished() {
        return game.isFinished();
    }

    /**
     * Obtém a cor do próximo jogador a fazer um movimento.
     *
     * @return A Cor do próximo a jogar.
     */
    public PieceColor nextPlayer() {
        return game.nextPlayer();
    }

    /**
     * Aceita um movimento de peça. O movimento é processado alternadamente entre branco e preto.
     * Se for dado um movimento com a cor de peça errada, a ação não é realizada.
     *
     * @param piece A peça a mover.
     * @param initPosition A posição inicial da peça.
     * @param endPosition A posição final da peça.
     */
    public void play(Piece piece, Position initPosition, Position endPosition) {
        game.play(piece, initPosition, endPosition);
    }

    /**
     * Aceita um movimento de peça a partir de strings de posição. O movimento é processado
     * alternadamente entre branco e preto. Se for dado um movimento com a cor de peça errada,
     * a ação não é realizada.
     *
     * @param initPosition A posição inicial da peça, como "A2" ou "B7", etc.
     * @param endPosition A posição final da peça, como "A2" ou "B7", etc.
     */
    public void play(String initPosition, String endPosition) {
        game.play(initPosition, endPosition);
    }

    /**
     * Reinicia o jogo, recolocando todas as peças nas suas posições iniciais.
     * Após o reinício, o tabuleiro na interface gráfica é redesenhado para refletir o estado atual.
     */
    public void resetGame() {
        game.resetGame();
        drawBoard();
    }

    /**
     * Retorna a cor vencedora se o jogo tiver terminado.
     *
     * @return A Cor do último rei em jogo se o jogo tiver terminado, ou null se o jogo não tiver terminado.
     */
    public PieceColor whoWon() {
        return game.whoWon();
    }

    private void drawBoard() {
        boardPanel.removeAll();
        List<Square> board = game.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel squareUI = new JPanel();
                squareUI.setLayout(new BorderLayout());

                boolean isLightSquare = (row + col) % 2 == 0;
                if (isLightSquare) {
                    squareUI.setBackground(LIGHT_SQUARE_COLOR);
                } else {
                    squareUI.setBackground(DARK_SQUARE_COLOR);
                }

                final int currentIndex = (row * 8) + col;

                final Position currentPosition;
                final Piece pieceInSquare;

                if (currentIndex >= 0 && currentIndex < board.size()) {
                    Square currentSquare = board.get(currentIndex);
                    pieceInSquare = currentSquare.getPiece();
                    currentPosition = currentSquare.getPosition();
                } else {
                    currentPosition = new Position((char)('A' + col), 8 - row);
                    pieceInSquare = null;
                    System.err.println("Erro: Índice do tabuleiro fora dos limites: " + currentIndex);
                }

                if (pieceInSquare != null) {
                    String pieceKey = (pieceInSquare.getColor() == PieceColor.WHITE ? "white" : "black") + pieceInSquare.getClass().getSimpleName();
                    String imagePath = pieceImagePaths.get(pieceKey);

                    if (imagePath != null) {
                        ImageIcon pieceIcon = loadImage(imagePath, SQUARE_SIZE, SQUARE_SIZE);
                        if (pieceIcon != null) {
                            JLabel pieceLabel = new JLabel(pieceIcon);
                            pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            pieceLabel.setVerticalAlignment(SwingConstants.CENTER);
                            squareUI.add(pieceLabel, BorderLayout.CENTER);
                        } else {
                            System.err.println("Erro: Imagem para " + pieceKey + " não encontrada ou não pôde ser redimensionada.");
                        }
                    } else {
                        System.err.println("Erro: Caminho da imagem não mapeado para " + pieceKey + ". Verifique initializePieceImagePaths().");
                    }
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
                } else {
                    System.out.println("Não é a vez dessa cor jogar.");
                }
            } else {
                System.out.println("Nenhuma peça para selecionar nesta casa.");
            }
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

                if (game.isFinished()) {
                    showGameOverDialog();
                }
            }
        }
    }

    private Color getOriginalSquareColor(int index) {
        int row = index / 8;
        int col = index % 8;

        boolean isLightSquare = (row + col) % 2 == 0;
        return isLightSquare ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
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

    private ImageIcon loadImage(String path, int width, int height) {
        URL imageUrl = getClass().getClassLoader().getResource(path);
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            return null;
        }
    }

    /**
     * Exibe um diálogo personalizado de fim de jogo, informando o vencedor e oferecendo opções para
     * jogar novamente ou sair do programa.
     */
    private void showGameOverDialog() {
        // Desativar a janela principal para que o foco fique no diálogo
        this.setEnabled(false);

        JDialog gameOverDialog = new JDialog(this, "Fim do Jogo", true);
        gameOverDialog.setLayout(new BorderLayout(10, 10)); // Espaçamento entre componentes
        gameOverDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Impede fechar pelo 'X'

        PieceColor winner = game.whoWon();
        String winnerMessage;
        if (winner != null) {
            winnerMessage = (winner == PieceColor.WHITE ? "As peças Brancas" : "As peças Pretas") + " venceram!";
        } else {
            winnerMessage = "O jogo terminou em empate!";
        }

        // Painel para a mensagem
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(230, 230, 230)); // Um cinzento claro
        JLabel messageLabel = new JLabel("<html><center><br>"+ winnerMessage + "<br><br>Gostaria de jogar novamente?</center></html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messagePanel.add(messageLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Espaçamento entre botões
        buttonPanel.setBackground(new Color(230, 230, 230));

        JButton playAgainButton = new JButton("Jogar Novamente");
        playAgainButton.setFont(new Font("Arial", Font.PLAIN, 16));
        playAgainButton.setBackground(new Color(100, 180, 100)); // Verde
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setFocusPainted(false); // Remove o contorno de foco
        playAgainButton.addActionListener(e -> {
            gameOverDialog.dispose(); // Fecha o diálogo
            resetGame();             // Reinicia o jogo
            this.setEnabled(true);   // Reativa a janela principal
        });

        JButton exitButton = new JButton("Sair do Jogo");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setBackground(new Color(200, 80, 80)); // Vermelho
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);

        gameOverDialog.add(messagePanel, BorderLayout.CENTER);
        gameOverDialog.add(buttonPanel, BorderLayout.SOUTH);

        gameOverDialog.pack(); // Ajusta o tamanho do diálogo ao conteúdo
        gameOverDialog.setLocationRelativeTo(this); // Centraliza em relação à janela principal
        gameOverDialog.setVisible(true); // Torna o diálogo visível
    }
}