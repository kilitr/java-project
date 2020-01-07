package de.kilitr;


public class Main {
    public static void main(String[] args) {
        UndirectedGraph undirectedGraph =  new GraphLoader("implement_dijkstra.graphml").getUndirectedGraph();
        UndirectedDijkstra uDijk = new UndirectedDijkstra(undirectedGraph, undirectedGraph.getVertex("a"));
        uDijk.execute();
        uDijk.getDistances().forEach((k,v) -> {
            System.out.format("Vertex= %s, distance=%d%n", k, v);
        });
    }
}
