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

        String vertexName = "n0";
        logger.info("Calculating shortest paths from Vertex \"" + vertexName + "\"");
        /*Dijkstra uDijk = new Dijkstra(undirectedGraph, undirectedGraph.getVertex(vertexName));
        uDijk.execute();

        logger.info("Path to n12 = " + uDijk.getVertexPathTo(undirectedGraph.getVertex("n12")));*/
        logger.info("Graph is connected? " + undirectedGraph.isConnected());

        Dijkstra d2 = new Dijkstra(undirectedGraph, undirectedGraph.getVertex("n0"));
        logger.info("Dijkstra: " + d2.execute());
        logger.info("Dijkstra: " + d2.getDistances());
        for(Vertex v : undirectedGraph.getVertices()) {
            Paths tempPaths = d2.createAllShortestPaths(v);
            logger.info(tempPaths.size() + " Path(s) to " + v.getLabel() + " (" + d2.getDistanceTo(v) + ") = " + d2.createAllShortestPaths(v));
        }
        Betweenness g1 = new Betweenness(undirectedGraph);
        List<List<Paths>> l1 = new ArrayList<List<Paths>>(g1.getAllPaths());
    }
}
