package chess.core;

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
import java.util.List;

public class XML {

    /**
     * Gera um ficheiro XML que representa o estado do tabuleiro de xadrez,
     * seguindo a estrutura definida no DTD 'xadrez.dtd'.
     *
     * @param board - Tabuleiro
     */
    public static void create(List<Square> board) {
        int row = 8;
        char col = 'A';

        Document documentTabuleiro;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            documentTabuleiro = dBuilder.newDocument();

            Element rootElement = documentTabuleiro.createElement("Tabuleiro");
            documentTabuleiro.appendChild(rootElement);

            for (int i = 0; i < board.size(); i++) {
                Square square = board.get(i);
                if (!square.isEmpty()) {
                    Element pieceElement = documentTabuleiro.createElement("Peca");
                    pieceElement.setAttribute("id", String.valueOf(i));
                    pieceElement.setAttribute("cor", square.getPiece().getColor().toString());
                    pieceElement.setAttribute("tipo", square.getPiece().getClass().getSimpleName());

                    Element posElement = documentTabuleiro.createElement("posição");
                    posElement.appendChild(documentTabuleiro.createTextNode("" + col + row));
                    pieceElement.appendChild(posElement);

                    rootElement.appendChild(pieceElement);
                }
                if (col == 'H') {
                    col = 'A';
                    row--;
                } else col++;
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
