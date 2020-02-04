package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class ResultConsolePrinter extends JavaProjectThread {
    private static final Logger logger = LogManager.getLogger(ResultConsolePrinter.class);

    private Results results;

    public ResultConsolePrinter(Results results, Vertex start, Vertex destination) {
        super(results, start, destination);
        this.results = results;
    }

    public ResultConsolePrinter(Results results, Vertex start) {
        super(results, start);
        this.results = results;
    }

    public ResultConsolePrinter(Results results, boolean all) {
        super(results, all);
        this.results = results;
    }

    private void basicGraphInformation(Results results) {
        logger.info("### Graph information ###");
        logger.info("\t\tAmount of Vertices: " + results.getAmountVertices());
        logger.info("\t\tAmount of Edges: " + results.getAmountEdges());
        logger.info("\t\tVertex labels: " + String.join(", ", results.getVertexLabels()));
        logger.info("\t\tEdges: " + String.join(", ", results.getEdgeLabels()));
        logger.info("\t\tGraph is connected? " + results.isConnected());
        if (allFlag) {
            logger.info("\t\tGraph diameter: " + results.getDiameter());
        } else {
            logger.info("\t\tGraph diameter: only with -a flag");
        }
    }

    private static void allShortestPaths(Results results) {
        logger.info("### Shortest paths ###");
        for (Map.Entry<Vertex, TreeMap<Vertex, Path>> allPathsEntry : results.getAllPaths().entrySet()) {
            logger.info("Source node '" + allPathsEntry.getKey() + "'");
            for (Map.Entry<Vertex, Path> pathEntry : allPathsEntry.getValue().entrySet()) {
                Vertex to = pathEntry.getKey();
                int weight = pathEntry.getValue().getWeight();
                Path actualPath = pathEntry.getValue();
                logger.info("\t\t To node '" + to.getLabel() + "' with cost " + weight + ": " + actualPath.toString());
            }
        }
    }

    private static void shortestPaths(Results results, Vertex start, Vertex destination) {
        logger.info("### Paths from '" + start.getLabel() + "' to '" + destination.getLabel() + "' ###");
        Path paths = results.getAllPaths().get(start).get(destination);
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

    @Override
    public void run() {
        basicGraphInformation(results);
        logger.debug("allFlag = " + allFlag + "\nsinglePathFlag = " + singlePathFlag + "\nsingleBetweennessFlag = " + singleBetweennessFlag);
        if (allFlag) {
            allShortestPaths(results);
            allBetweennessCentrality(results);
        } else if (singlePathFlag) {
            shortestPaths(results, start, destination);
        } else if (singleBetweennessFlag) {
            betweennessCentrality(results, start);
        }
    }
}
