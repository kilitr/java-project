package de.kilitr;

import de.kilitr.exceptions.GraphNotValidException;
import de.kilitr.exceptions.VertexNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandLineInterface {
    private static final Logger logger = LogManager.getLogger(CommandLineInterface.class);

    public static void main(String[] args) {
        if (args.length > 0) {
            // always read the Graph first.
            String inputFile = args[0];
            logger.info("Loading graph from file: \"" + inputFile + "\".");
            GraphLoader graphLoader = new GraphLoader(inputFile);
            Graph graph = null; // will not stay null
            try {
                graph = graphLoader.getUndirectedGraph();
            } catch (GraphNotValidException e) {
                logger.error("Error: " + e.getMessage());
                System.exit(-1);
            }

            basicGraphInformation(graph);

            // parsing the Arguments
            for (String arg : args) {
                if (arg.equals("-s")) {
                    Vertex start = getVertex(graph, args[2]);
                    Vertex destination = getVertex(graph, args[3]);
                    shortestPaths(graph, start, destination);
                }
                if (arg.equals("-b")) {
                    Vertex v = getVertex(graph, args[2]);
                    betweennessCentrality(graph, v);
                }
                if (arg.equals("-o")) {
                    // TODO: save to output
                }
                if (arg.equals("--all") || arg.equals("-a")) { // all information needed...
                    allShortestPaths(graph);
                    allBetweennessCentrality(graph);
                }
            }
        } else {
            helpMessage();
        }
    }

    private static Vertex getVertex(Graph graph, String label) {
        Vertex v = null;
        try {
            v = graph.getVertex(label);
        } catch (VertexNotFoundException e) {
            logger.error("Error: " + e.getMessage());
            System.exit(-1);
        }
        return v;
    }

    private static void basicGraphInformation(Graph graph) {
        logger.info("### Graph information ###");
        logger.info("\t\tAmount of Vertices: " + graph.getNumberOfVertices());
        logger.info("\t\tAmount of Edges: " + graph.getNumberOfEdges());
        logger.info("\t\tVertex labels: " + String.join(", ", graph.getVertexLabels()));
        logger.info("\t\tEdges: " + String.join(", ", graph.getEdgeLabels()));
        logger.info("\t\tGraph is connected? " + graph.isConnected());
        logger.info("\t\tGraph diameter: not implemented yet"); // TODO: implement graph diameter
    }

    private static void allShortestPaths(Graph graph) {
        logger.info("### Shortest paths ###");
        for (Vertex start : graph.getVertices()) {
            logger.info("Source node '" + start.getLabel() + "'");
            Dijkstra dijkstra = new Dijkstra(graph, start);
            for (Vertex target : graph.getVertices()) {
                Paths paths = dijkstra.createAllShortestPaths(target);
                logger.info("\t\t To node '" + target.getLabel() + "' with cost " + paths.getWeight() + ": " + paths.toString());
            }
        }
    }

    private static void shortestPaths(Graph graph, Vertex start, Vertex destination) {
        logger.info("### Paths from '" + start.getLabel() + "' to '" + destination.getLabel() + "' ###");
        Dijkstra dijkstra = new Dijkstra(graph, start);
        Paths paths = dijkstra.createAllShortestPaths(destination);
        logger.info("\t\t" + paths.toString() + " length -> " + paths.getWeight());
    }

    private static void allBetweennessCentrality(Graph graph) {
        logger.info("### Betweenness centrality ###");
        Betweenness betweenness = new Betweenness(graph);
        for (Vertex v : graph.getVertices()) {
            double betweennessValue = betweenness.getBetweenness(v);
            logger.info("\t\tNode '" + v.getLabel() + "': " + betweennessValue);
        }
    }

    private static void betweennessCentrality(Graph graph, Vertex v) {
        logger.info("### Betweenness centrality ###");
        Betweenness betweenness = new Betweenness(graph);
        double betweennessValue = betweenness.getBetweenness(v);
        logger.info("\t\tNode '" + v.getLabel() + "': " + betweennessValue);
    }

    private static void helpMessage() {
        logger.warn("Proper Usage is: java-project <inputfile> [option] [-o <outputfile>]");
        logger.info("\tYou may also use (only) ONE of the following options:");

        String allHelp = "Provide all the information about the graph, that this program can collect.";
        String dijkstraHelp = "Calculate the shortest paths from start to destination.";
        String betweennessHelp = "Calculate the betweenness centrality for node.";

        logger.info("\t\t-a | --all\t\t\t\t\t - \t" + allHelp);
        logger.info("\t\t-s <start> <destination>\t - \t" + dijkstraHelp);
        logger.info("\t\t-b <node>\t\t\t\t\t - \t" + betweennessHelp);
    }
}
