package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * A class that can calculate the betweenness centrality of an node in a graph
 */
public class Betweenness {
    private static final Logger logger = LogManager.getLogger(Betweenness.class);

    private final Graph graph;
    private final List<Path> paths;

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
        for (Node v : this.graph.getVertices()) {
            Dijkstra paths = new Dijkstra(graph, v);
            for (Node w : this.graph.getVertices()) {
                allPathlist.add(paths.createAllShortestPaths(w));
            }
            logger.debug("Calculated all shortest paths from " + v.getLabel());

        }
        logger.debug("All shortest paths were calculated successfully.");
        return allPathlist;
    }

    /**
     * provides the betweenness centrality measure for the given node.
     *
     * @param node a node of the graph
     * @return the betweenness centrality of the node v
     */
    public double getBetweenness(Node node) {
        double betweenness = 0;
        for (Path p : this.paths) {
            betweenness = betweenness + ((double) p.checkNode(node)) / ((double) p.size());
        }
        return betweenness / 2;
    }





}
