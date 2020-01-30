package de.kilitr;

import java.nio.file.Path;
import java.util.*;

public class Betweenness {
    private Graph graph;
    private ArrayList<Vertex> vertices;

    public Betweenness(Graph g) {
        this.graph = g;
        this.vertices = g.getVertices();
    }

    public List<List<Paths>> getAllPaths(){
        List<List<Paths>> allPathlist = new ArrayList<List<Paths>>();
        int i = this.graph.getNumberOfVertices();
        for(Vertex v : this.graph.getVertices()) {
            List<Paths> pathlist = new ArrayList<Paths>();
            Dijkstra paths = new Dijkstra(graph, v);
            for (Vertex w : this.graph.getVertices()){
                pathlist.add(paths.createAllShortestPaths(w));
            }
            allPathlist.add(pathlist);

        }
        return allPathlist;
    }
/*
    public double getBetweenness(Vertex v){
        double betweenness = 0;
        List<Paths> pathlist = new ArrayList<Paths>(getAllPaths());
        for(Paths p : pathlist) {

        }

        return betweenness;

    }

 */
}
