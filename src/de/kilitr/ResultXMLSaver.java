package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * allow saving the class Results to an XML file utilizing its own Thread.
 *
 * @see de.kilitr.Results
 */
public class ResultXMLSaver extends ArgumentRelatedThread {
    private static final Logger logger = LogManager.getLogger(ResultXMLSaver.class);

    private final Results results;
    private final String xmlFilePath;

    private Document xmlDoc;
    private Element basicInformation;
    private Element paths;
    private Element betweenness;

    /**
     * Constructor for the shortest path between two vertices command line argument.
     *
     * @param outputFileName the name or path of the XML file to be created.
     * @param results        contains every calculated value &amp; path for this command line argument.
     * @param start          the start node of the path
     * @param destination    the destination node of the path
     */
    public ResultXMLSaver(String outputFileName, Results results, Node start, Node destination) {
        super(start, destination);
        this.results = results;
        this.xmlFilePath = outputFileName;
    }

    /**
     * Constructor for the betweenness centrality measure of a node command line argument.
     *
     * @param outputFileName the name or path of the XML file to be created.
     * @param results        contains every calculated value &amp; path for this command line argument.
     * @param start          calculate the betweenness centrality for this node.
     */
    public ResultXMLSaver(String outputFileName, Results results, Node start) {
        super(start);
        this.results = results;
        this.xmlFilePath = outputFileName;
    }

    /**
     * Constructor for the command line argument for calculating everything.
     *
     * @param outputFileName the name or path of the XML file to be created.
     * @param results        contains every calculated value &amp; path for this command line argument.
     * @param all            the parameter is only used for changing the signature of the Constructor. -&gt; must be set to true though
     */
    public ResultXMLSaver(String outputFileName, Results results, boolean all) {
        super(all);
        this.results = results;
        this.xmlFilePath = outputFileName;
    }


    private void createShortestPathsNode(Node start, Node destination) {
        Element allPathsElement = xmlDoc.createElement("AllPaths");
        paths.appendChild(allPathsElement);
        allPathsElement.setAttribute("start", start.getLabel());

        Path allPaths = results.getAllPaths().get(start).get(destination);
        for (LinkedList<Node> pathList : allPaths.getPaths()) {
            Element singlePath = xmlDoc.createElement("path");
            allPathsElement.appendChild(singlePath);
            for (int i = 0; i < pathList.size(); i++) {
                singlePath.setAttribute("stop" + i, pathList.get(i).getLabel());
            }
            singlePath.appendChild(xmlDoc.createTextNode(Integer.toString(allPaths.getWeight())));
        }
    }

    private void createAllShortestPathsNode() {
        for (Map.Entry<Node, TreeMap<Node, Path>> allPathsEntry : results.getAllPaths().entrySet()) {
            Element allPathsElement = xmlDoc.createElement("AllPaths");
            paths.appendChild(allPathsElement);
            allPathsElement.setAttribute("start", allPathsEntry.getKey().getLabel());

            for (Map.Entry<Node, Path> pathEntry : allPathsEntry.getValue().entrySet()) {
                Element pathsTo = xmlDoc.createElement("paths");
                allPathsElement.appendChild(pathsTo);
                pathsTo.setAttribute("to", pathEntry.getKey().getLabel());

                int weight = pathEntry.getValue().getWeight();

                Path actualPath = pathEntry.getValue();
                for (LinkedList<Node> pathList : actualPath.getPaths()) {
                    Element singlePath = xmlDoc.createElement("path");
                    pathsTo.appendChild(singlePath);
                    for (int i = 0; i < pathList.size(); i++) {
                        singlePath.setAttribute("stop" + i, pathList.get(i).getLabel());
                    }
                    singlePath.appendChild(xmlDoc.createTextNode(Integer.toString(weight)));
                }
            }
        }
    }

    private void createAllBetweennessNode() {
        for (Map.Entry<Node, Double> betweennessEntry : results.getAllBetweenness().entrySet()) {
            Element betweennessForNode = xmlDoc.createElement(betweennessEntry.getKey().getLabel());
            this.betweenness.appendChild(betweennessForNode);
            betweennessForNode.setAttribute("betweenness", Double.toString(betweennessEntry.getValue()));
        }
    }

    private void createBetweennessNode(Node node) {
        Element betweennessForNode = xmlDoc.createElement(node.getLabel());
        this.betweenness.appendChild(betweennessForNode);
        betweennessForNode.setAttribute("betweenness", Double.toString(results.getAllBetweenness().get(node)));
    }

    private void createBasicInformationNode() {
        basicInformation.setAttribute("numNodes", Integer.toString(results.getAmountNodes()));
        basicInformation.setAttribute("numEdges", Integer.toString(results.getAmountEdges()));
        basicInformation.setAttribute("connected", Boolean.toString(results.isConnected()));
        if (allFlag) {
            basicInformation.setAttribute("diameter", Integer.toString(results.getDiameter()));
        }

        for (String nodeLabel : results.getNodeLabels()) {
            Element node = xmlDoc.createElement("node");
            node.appendChild(xmlDoc.createTextNode(nodeLabel));
            basicInformation.appendChild(node);
        }

        for (Edge e : results.getEdges()) {
            Element edge = xmlDoc.createElement("edge");
            basicInformation.appendChild(edge);
            edge.setAttribute("from", e.getFrom().toString());
            edge.setAttribute("to", e.getTo().toString());
            edge.setAttribute("weight", Integer.toString(e.getWeight()));
        }
    }

    public void run() {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            xmlDoc = documentBuilder.newDocument();

            Element root = xmlDoc.createElement("graphResults");
            xmlDoc.appendChild(root);

            basicInformation = xmlDoc.createElement("basicInformation");
            root.appendChild(basicInformation);
            createBasicInformationNode();

            if (allFlag) {
                paths = xmlDoc.createElement("allPaths");
                root.appendChild(paths);
                createAllShortestPathsNode();

                betweenness = xmlDoc.createElement("allBetweenness");
                root.appendChild(betweenness);
                createAllBetweennessNode();
            } else if (singlePathFlag) {
                paths = xmlDoc.createElement("allPaths");
                root.appendChild(paths);
                createShortestPathsNode(start, destination);
            } else if (singleBetweennessFlag) {
                betweenness = xmlDoc.createElement("allBetweenness");
                root.appendChild(betweenness);
                createBetweennessNode(start);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(xmlDoc);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException e) {
            logger.error("XML Saving-Preparation Error: " + e.getMessage());
            logger.debug(e.getStackTrace());
        } catch (TransformerException e) {
            logger.error("XML Saving Error: " + e.getMessage());
            logger.debug(e.getStackTrace());
        }
    }
}