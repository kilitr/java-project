package de.kilitr;

import de.kilitr.exceptions.GraphNotValidException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String filename = "small_graph.graphml";
        logger.info("Loading Graph: \"" + filename + "\".");
        UndirectedGraph undirectedGraph = null;
        try {
            undirectedGraph = new GraphLoader(filename).getUndirectedGraph();
        } catch (GraphNotValidException e) {
            logger.error(e.getMessage());
            System.exit(-2);
        }
        logger.info("Graph is connected? " + undirectedGraph.isConnected());

        String vertexName = "n0";
        logger.info("Calculating shortest paths from Vertex \"" + vertexName + "\"");
        Dijkstra d2 = new Dijkstra(undirectedGraph, undirectedGraph.getVertex("n0"));
        for(Vertex v : undirectedGraph.getVertices()) {
            Paths tempPaths = d2.createAllShortestPaths(v);
            logger.info(tempPaths.size() + " Path(s) to " + v.getLabel() + " (" + d2.getDistanceTo(v) + ") = " + d2.createAllShortestPaths(v));
        }
    }
}
