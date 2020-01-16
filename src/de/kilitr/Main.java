package de.kilitr;


public class Main {
    public static void main(String[] args) {
        UndirectedGraph undirectedGraph =  new GraphLoader("small_graph.graphml").getUndirectedGraph();
        Dijkstra uDijk = new Dijkstra(undirectedGraph, undirectedGraph.getVertex("n0"));
        uDijk.execute();

        System.out.println("Path to f = " + uDijk.getPathTo(undirectedGraph.getVertex("n12")));
        System.out.println("Graph is connected? " + undirectedGraph.isConnected());
    }
}
