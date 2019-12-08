import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class GraphLoader {
    private File inputFile;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    private NodeList xmlListVertices;
    private NodeList xmlListEdges;

    public GraphLoader(String filename) {
        try {
            inputFile = new File(filename);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            loadVertices();
            loadEdges();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVertices() {
        xmlListVertices = doc.getElementsByTagName("node");
        for (int temp = 0; temp < xmlListVertices.getLength(); temp++) {
            Node nNode = xmlListVertices.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String n_id = eElement.getAttribute("id");
                String v_id = eElement.getElementsByTagName("data").item(0).getTextContent();
                System.out.println(nNode.getNodeName() + " id: " + n_id + "\tv_id: " + v_id);
            }
        }
    }

    private void loadEdges() {
        xmlListEdges = doc.getElementsByTagName("edge");
        for (int temp = 0; temp < xmlListEdges.getLength(); temp++) {
            Node nNode = xmlListEdges.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String source = eElement.getAttribute("source");
                String target = eElement.getAttribute("target");
                String e_id = eElement.getElementsByTagName("data").item(0).getTextContent();
                String e_weight = eElement.getElementsByTagName("data").item(1).getTextContent();
                System.out.println(nNode.getNodeName() + " source: " + source + "\ttarget: " + target +
                        "\te_id: " + e_id + "\te_weight: " + e_weight);
            }
        }
    }
}
