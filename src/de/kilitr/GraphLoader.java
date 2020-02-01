package de.kilitr;

import de.kilitr.exceptions.DuplicateVertexException;
import de.kilitr.exceptions.GraphNotValidException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * Helper class that offers an easy interface to load a graph described by an graphml file.
 */
public class GraphLoader {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    private File inputFile;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    private NodeList xmlListVertices;
    private NodeList xmlListEdges;

    private UndirectedGraph undirectedGraph;

    /**
     * loads the graph and automatically generates the Graph object.
     * @param filename (path) of the graphml file to process.
     */
    public GraphLoader(String filename) {
        try {
            logger.debug("Parsing \"" + filename + "\"");
            inputFile = new File(filename);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            loadVertices();
            loadEdges();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Error: " + e.getMessage());
        }
    }

    private void loadVertices() {
        xmlListVertices = doc.getElementsByTagName("node");
        String[] vertices = new String[xmlListVertices.getLength()];
        for (int temp = 0; temp < xmlListVertices.getLength(); temp++) {
            Node nNode = xmlListVertices.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String n_id = eElement.getAttribute("id");
                vertices[temp] = n_id;
            }
        }

        try {
            undirectedGraph = new UndirectedGraph(vertices);
        } catch (DuplicateVertexException e) {
            logger.error("Error: " + e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
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

                int e_weight = Integer.parseInt(
                        eElement.getElementsByTagName("data")
                                .item(1)
                                .getTextContent()
                );
                undirectedGraph.addEdge(source, target, e_weight);
            }
        }
    }

    /**
     * provides an Object of UndirectedGraph loaded with the information of the given *.graphml file.
     * @return The undirected graph, described by the provided *.graphml file.
     * @throws GraphNotValidException when an error occured during loading the graph.
     */
    public UndirectedGraph getUndirectedGraph() throws GraphNotValidException {
        if (this.undirectedGraph == null) {
            throw new GraphNotValidException("Something went wrong during loading the graph.");
        }
        logger.debug("Succesfully loaded graph");
        return this.undirectedGraph;
    }
}
