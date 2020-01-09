package de.kilitr;


public class Main {
    public static void main(String[] args) {
        UndirectedGraph undirectedGraph =  new GraphLoader("small_graph.graphml").getUndirectedGraph();
        Dijkstra uDijk = new Dijkstra(undirectedGraph, undirectedGraph.getVertex("n0"));
        uDijk.execute();

        //System.out.println("Path to f = " + uDijk.getPathTo(undirectedGraph.getVertex("f")));
        //System.out.println("Weight to f = " + uDijk.getWeightTo(undirectedGraph.getVertex("f")));*/


        DirectedGraph directedGraph =  new GraphLoader("small_graph.graphml").getDirectedGraph();
        Dijkstra dDijk = new Dijkstra(directedGraph, directedGraph.getVertex("n0"));
        dDijk.execute();

        //System.out.println("Path to f = " + dDijk.getPathTo(undirectedGraph.getVertex("f")));
        //System.out.println("Weight to f = " + dDijk.getWeightTo(undirectedGraph.getVertex("f")));
    }
}
