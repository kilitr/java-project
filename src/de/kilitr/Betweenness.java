package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * A class that can calculate the betweenness centrality of an vertex in a graph
 */
public class Betweenness {
    private static final Logger logger = LogManager.getLogger(Betweenness.class);

    private Graph graph;
    private List<Path> paths;

    /**
     * Initializes the betweenness centrality measure calculation.
     *
     * @param g graph, that the betweenness centrality measure should be calculated for.
     */
    public Betweenness(Graph g) {
        this.graph = g;
        this.paths = getAllPaths();
    }

    /**
     * @return an Arraylist of paths with all shortest paths of an graph
     */
    private List<Path> getAllPaths() {
        List<Path> allPathlist = new ArrayList<>();
        int i = this.graph.getNumberOfVertices();
        for (Vertex v : this.graph.getVertices()) {
            Dijkstra paths = new Dijkstra(graph, v);
            for (Vertex w : this.graph.getVertices()) {
                allPathlist.add(paths.createAllShortestPaths(w));
            }
            logger.debug("Calculated all shortest paths from " + v.getLabel());

        }
        logger.debug("All shortest paths were calculated successfully.");
        return allPathlist;
    }

    /**
     * provides the betweenness centrality measure for the given vertex.
     * @param v a vertex of the graph
     * @return the betweenness centrality of the vertex v
     */
    public double getBetweenness(Vertex v){
        double betweenness = 0;
        for (Path p : this.paths) {
            betweenness = betweenness + ((double) p.checkVertex(v)) / ((double) p.size());
        }
        return betweenness/2;
    }





}
