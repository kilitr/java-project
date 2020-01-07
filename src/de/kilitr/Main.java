package de.kilitr;


public class Main {
    public static void main(String[] args) {
        UndirectedGraph undirectedGraph =  new GraphLoader("implement_dijkstra.graphml").getUndirectedGraph();
        DirectedGraph directedGraph =  new GraphLoader("implement_dijkstra.graphml").getDirectedGraph();

        Dijkstra uDijk = new Dijkstra(undirectedGraph, undirectedGraph.getVertex("a"));
        uDijk.execute();
        Dijkstra dDijk = new Dijkstra(directedGraph, directedGraph.getVertex("a"));
        dDijk.execute();
    }
}
