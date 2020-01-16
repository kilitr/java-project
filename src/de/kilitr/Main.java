package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String filename = "small_graph.graphml";
        logger.info("Loading Graph: \"" + filename + "\".");
        UndirectedGraph undirectedGraph = new GraphLoader(filename).getUndirectedGraph();

        String vertexName = "n0";
        logger.info("Calculating shortest paths from Vertex \"" + vertexName + "\"");
        Dijkstra uDijk = new Dijkstra(undirectedGraph, undirectedGraph.getVertex(vertexName));
        uDijk.execute();

        logger.info("Path to f = " + uDijk.getPathTo(undirectedGraph.getVertex("n12")));
        logger.info("Graph is connected? " + undirectedGraph.isConnected());
    }
}
