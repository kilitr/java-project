package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.*;
/**
 * A class that can calculate the betweenness centrality of an vertex in a graph
 */
public class Betweenness {
    private static final Logger logger = LogManager.getLogger(Dijkstra.class);

    private Graph graph;
    private List<Paths> paths;

    public Betweenness(Graph g) {
        this.graph = g;
        this.paths = getAllPaths();
    }
    /**
     * @return an Arraylist of Paths with all shortest Paths of anGraph
     */
    private List<Paths> getAllPaths(){
        List<Paths> allPathlist = new ArrayList<Paths>();
        int i = this.graph.getNumberOfVertices();
        for(Vertex v : this.graph.getVertices()) {
            Dijkstra paths = new Dijkstra(graph, v);
            paths.execute();
            for (Vertex w : this.graph.getVertices()){
                allPathlist.add(paths.createAllShortestPaths(w));
            }
            logger.debug("Calculated all shortest paths from " + v.getLabel());

        }
        logger.debug("All shortest paths were calculated successfully.");
        return allPathlist;
    }

    /**
     * @param v a Vertex of the Graph
     * @return the betweenness centrality of an the Vertex v
     */
    public double getBetweenness(Vertex v){
        double betweenness = 0;
        for (Paths p : this.paths) {
            betweenness = betweenness + ((double)p.checkVertex(v))/((double)p.size());
        }
        return betweenness/2;
    }





}
