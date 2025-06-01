package chess.core;

import chess.core.board.pieces.Piece;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XML {

    /**
     * Gera um ficheiro XML que representa o estado do tabuleiro de xadrez,
     * seguindo a estrutura definida no DTD 'xadrez.dtd'.
     *
     * @param board - Tabuleiro
     */
    public static void create(List<Square> board) {
        List<Piece> blackPieces = new ArrayList<>();
        List<Piece> whitePieces = new ArrayList<>();
        List<String> allPositions = new ArrayList<>();
        List<String> blackPositions = new ArrayList<>();
        List<String> whitePositions = new ArrayList<>();

        int currentId = 0;

        for (int row = 8; row >= 1; row--) {
            for (char col = 'A'; col <= 'H'; col++) {
                allPositions.add("" + col + row);
            }
        }

        for (int i = 0; i < allPositions.size(); i++) {
            Piece piece = board.get(i).getPiece();
            if (piece != null) {
                if (piece.isBlack()) {
                    blackPieces.add(piece);
                    blackPositions.add(allPositions.get(i));
                } else {
                    whitePieces.add(piece);
                    whitePositions.add(allPositions.get(i));
                }
            }
        }

        Document documentTabuleiro;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            documentTabuleiro = dBuilder.newDocument();

            Element rootElement = documentTabuleiro.createElement("Tabuleiro");
            documentTabuleiro.appendChild(rootElement);

            Element blackPiecesElement = documentTabuleiro.createElement("PecasPretas");
            rootElement.appendChild(blackPiecesElement);
            for (int i = 0; i < blackPieces.size(); i++) {
                Piece piece = board.get(i).getPiece();
                char col = blackPositions.get(i).charAt(0);
                char row = blackPositions.get(i).charAt(1);

                Element pieceElement = documentTabuleiro.createElement("Peca");
                pieceElement.setAttribute("id", String.valueOf(currentId++));

                Element typeElement = documentTabuleiro.createElement("tipo");
                typeElement.appendChild(documentTabuleiro.createTextNode(piece.getClass().getSimpleName()));
                pieceElement.appendChild(typeElement);

                Element colElement = documentTabuleiro.createElement("coluna");
                colElement.appendChild(documentTabuleiro.createTextNode(col + ""));
                pieceElement.appendChild(colElement);

                Element rowElement = documentTabuleiro.createElement("linha");
                rowElement.appendChild(documentTabuleiro.createTextNode(row + ""));
                pieceElement.appendChild(rowElement);

                blackPiecesElement.appendChild(pieceElement);
            }

            Element whitePiecesElement = documentTabuleiro.createElement("PecasBrancas");
            rootElement.appendChild(whitePiecesElement);
            for (int i = 0; i < whitePieces.size(); i++) {
                Piece piece = board.get(i).getPiece();
                char col = whitePositions.get(i).charAt(0);
                char row = whitePositions.get(i).charAt(1);

                Element pieceElement = documentTabuleiro.createElement("Peca");
                pieceElement.setAttribute("id", String.valueOf(currentId++));

                Element typeElement = documentTabuleiro.createElement("tipo");
                typeElement.appendChild(documentTabuleiro.createTextNode(piece.getClass().getSimpleName()));
                pieceElement.appendChild(typeElement);

                Element colElement = documentTabuleiro.createElement("coluna");
                colElement.appendChild(documentTabuleiro.createTextNode(col + ""));
                pieceElement.appendChild(colElement);

                Element rowElement = documentTabuleiro.createElement("linha");
                rowElement.appendChild(documentTabuleiro.createTextNode(row + ""));
                pieceElement.appendChild(rowElement);

                whitePiecesElement.appendChild(pieceElement);
            }

            try (FileOutputStream fileOut = new FileOutputStream("savedFiles/xadrez.xml")) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "xadrez.dtd");

                DOMSource source = new DOMSource(documentTabuleiro);
                StreamResult result = new StreamResult(fileOut);

                transformer.transform(source, result);
                System.out.println("XML do tabuleiro de xadrez construído com sucesso em 'savedFiles/xadrez.xml'!");
            } catch (IOException | TransformerException e) {
                System.err.println("Erro ao guardar o XML do tabuleiro.");
            }

        } catch (ParserConfigurationException e) {
            System.err.println("Erro na configuração do parser XML.");
        }
    }
}
