package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String filename = "small_graph.graphml";
        logger.info("Loading Graph: \"" + filename + "\".");
        UndirectedGraph undirectedGraph = new GraphLoader(filename).getUndirectedGraph();
        logger.info("Graph is connected? " + undirectedGraph.isConnected());

        String vertexName = "n0";
        logger.info("Calculating shortest paths from Vertex \"" + vertexName + "\"");
        Dijkstra d2 = new Dijkstra(undirectedGraph, undirectedGraph.getVertex("n0"));
        d2.execute();
        for(Vertex v : undirectedGraph.getVertices()) {
            Paths tempPaths = d2.createAllShortestPaths(v);
            logger.info(tempPaths.size() + " Path(s) to " + v.getLabel() + " (" + d2.getDistanceTo(v) + ") = " + d2.createAllShortestPaths(v));
        }
        Betweenness g1 = new Betweenness(undirectedGraph);
        for (Vertex v : undirectedGraph.getVertices()) {
            double test = g1.getBetweenness(v);
            logger.info("Betweenness " + v.getLabel() +" \"" + test + "\"");
        }
    }
}
