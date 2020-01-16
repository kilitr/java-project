package de.kilitr;

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
 * A helper class that offers an easy interface to load a graph described by an graphml file.
 */
public class GraphLoader {
    private File inputFile;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    private NodeList xmlListVertices;
    private NodeList xmlListEdges;

    private UndirectedGraph undirectedGraph;

    /**
     * @param filename The filename / path of the graphml file to process.
     */
    public GraphLoader(String filename) {
        try {
            System.out.print("Parsing " + filename + " ...");
            inputFile = new File(filename);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            loadVertices();
            loadEdges();
            System.out.println(" succes!");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
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
     * @return The undirected graph, described by the provided graphml file.
     * @see de.kilitr.GraphLoader#GraphLoader(String)
     * @see de.kilitr.UndirectedGraph
     */
    public UndirectedGraph getUndirectedGraph() {
        if(this.undirectedGraph == null) {
            // throw new Exception("TODO: Custom Exception... When something undefined went wrong during loading");
        }
        System.out.println("Succesfully loaded undirected graph.");
        return this.undirectedGraph;
    }
}
