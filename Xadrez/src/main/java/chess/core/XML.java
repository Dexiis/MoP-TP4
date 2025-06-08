package chess.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
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
        int i = 1;

        Document documentTabuleiro;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            documentTabuleiro = dBuilder.newDocument();

            Element rootElement = documentTabuleiro.createElement("Tabuleiro");
            documentTabuleiro.appendChild(rootElement);

            Element blackPiecesElement = documentTabuleiro.createElement("PecasPretas");
            rootElement.appendChild(blackPiecesElement);

            Element whitePiecesElement = documentTabuleiro.createElement("PecasBrancas");
            rootElement.appendChild(whitePiecesElement);

            for (Square square : board) {
                if (!square.isEmpty()) {
                    Element pieceElement = documentTabuleiro.createElement("Peca");
                    pieceElement.setAttribute("id", String.valueOf(i++));
                    pieceElement.setAttribute("tipo", square.getPiece().getClass().getSimpleName());

                    Element posElement = documentTabuleiro.createElement("posição");
                    posElement.appendChild(documentTabuleiro.createTextNode(square.getPositionAsString()));
                    pieceElement.appendChild(posElement);

                    Element pieceColor = pathFinder(square.getPiece().getColor(), documentTabuleiro);
                    pieceColor.appendChild(pieceElement);
                }
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
        } catch (IOException | SAXException | XPathExpressionException e) {}
    }

    private static Element pathFinder(PieceColor color, Document documentTabuleiro) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        String expression;
        if (color == PieceColor.BLACK) expression = "/Tabuleiro/PecasPretas";
        else expression = "/Tabuleiro/PecasBrancas";

        return (Element) ((NodeList) xpath.evaluate(expression, documentTabuleiro, XPathConstants.NODESET)).item(0);
    }
}
