package de.kilitr;

public class Main {
    public static void main(String[] args) {
        UndirectedGraph undirectedGraph =  new GraphLoader("implement_dijkstra.graphml").getUndirectedGraph();
        System.out.println(undirectedGraph.getVertex("a").getEdges());
        UndirectedDijkstra uDijk = new UndirectedDijkstra(undirectedGraph, undirectedGraph.getVertex("a"));
        uDijk.execute();
    }
}
