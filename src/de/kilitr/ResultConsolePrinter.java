package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ResultConsolePrinter {
    private static final Logger logger = LogManager.getLogger(ResultConsolePrinter.class);

    public ResultConsolePrinter(Results results) {
        basicGraphInformation(results);
    }

    public ResultConsolePrinter(Results results, Vertex start, Vertex destination) {
        this(results);
        shortestPaths(results, start, destination);
    }

    public ResultConsolePrinter(Results results, Vertex start) {
        this(results);
        betweennessCentrality(results, start);
    }

    public ResultConsolePrinter(Results results, boolean all) {
        this(results);
        allShortestPaths(results);
        allBetweennessCentrality(results);
    }

    private static void basicGraphInformation(Results results) {
        logger.info("### Graph information ###");
        logger.info("\t\tAmount of Vertices: " + results.getAmountVertices());
        logger.info("\t\tAmount of Edges: " + results.getAmountEdges());
        logger.info("\t\tVertex labels: " + String.join(", ", results.getVertexLabels()));
        logger.info("\t\tEdges: " + String.join(", ", results.getEdgeLabels()));
        logger.info("\t\tGraph is connected? " + results.isConnected());
        logger.info("\t\tGraph diameter: " + results.getDiameter());
    }

    private static void allShortestPaths(Results results) {
        logger.info("### Shortest paths ###");
        for (Map.Entry<Vertex, PathsFromVertex> allPathsEntry : results.getAllPaths().entrySet()) {
            logger.info("Source node '" + allPathsEntry.getKey() + "'");
            for (Map.Entry<Vertex, Path> pathEntry : allPathsEntry.getValue().getAllDestinationPaths().entrySet()) {
                Vertex to = pathEntry.getKey();
                int weight = pathEntry.getValue().getWeight();
                Path actualPath = pathEntry.getValue();
                logger.info("\t\t To node '" + to.getLabel() + "' with cost " + weight + ": " + actualPath.toString());
            }
        }
    }

    private static void shortestPaths(Results results, Vertex start, Vertex destination) {
        logger.info("### Paths from '" + start.getLabel() + "' to '" + destination.getLabel() + "' ###");
        Path paths = results.getAllPaths().get(start).getPath(destination);
        logger.info("\t\t" + paths.toString() + " length -> " + paths.getWeight());
    }

    private static void allBetweennessCentrality(Results results) {
        logger.info("### Betweenness centrality ###");

        for (Map.Entry<Vertex, Double> betweennessEntry : results.getAllBetweenness().entrySet()) {
            String vertexLabel = betweennessEntry.getKey().getLabel();
            double betweennessValue = betweennessEntry.getValue();
            logger.info("\t\tNode '" + vertexLabel + "': " + betweennessValue);
        }
    }

    private static void betweennessCentrality(Results results, Vertex v) {
        logger.info("### Betweenness centrality ###");
        logger.info("\t\tNode '" + v.getLabel() + "': " + results.getAllBetweenness().get(v));
    }
}
