package de.kilitr;

import java.nio.file.Path;
import java.util.*;

public class Betweenness {
    private Graph graph;
    private List<Paths> paths;

    public Betweenness(Graph g) {
        this.graph = g;
        this.paths = getAllPaths();
    }

    public List<Paths> getAllPaths(){
        List<Paths> allPathlist = new ArrayList<Paths>();
        int i = this.graph.getNumberOfVertices();
        for(Vertex v : this.graph.getVertices()) {
            Dijkstra paths = new Dijkstra(graph, v);
            paths.execute();
            for (Vertex w : this.graph.getVertices()){
                allPathlist.add(paths.createAllShortestPaths(w));
            }

        }
        return allPathlist;
    }

    public double getBetweenness(Vertex v){
        double betweenness = 0;
        for (Paths p : this.paths) {
            betweenness = betweenness + p.checkVertex(v)/p.size();
        }
        return betweenness/2;
    }





}
