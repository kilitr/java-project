package de.kilitr;


public class Main {
    public static void main(String[] args) {

        GraphLoader graphLoader = new GraphLoader("small_graph.graphml");
        UndirectedGraph undirectedGraph = graphLoader.getUndirectedGraph();

    }
}
