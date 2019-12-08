package de.kilitr;


public class Main {
    public static void main(String[] args) {

        String[] vertices = new String[5];
        vertices[0] = "v0";
        vertices[1] = "v1";
        vertices[2] = "v2";
        vertices[3] = "v3";
        vertices[4] = "v4";
        UndirectedGraph undirectedGraph = new UndirectedGraph(vertices);
        undirectedGraph.addEdge("v1", "v2");
        undirectedGraph.addEdge("v2", "v3");
        undirectedGraph.addEdge("v3", "v4");
        undirectedGraph.addEdge("v4", "v5");

        UndirectedGraph weightedUndirectedGraph = new UndirectedGraph(vertices);
        weightedUndirectedGraph.addEdge("v1", "v2", 3);
        weightedUndirectedGraph.addEdge("v2", "v3", 4);
        weightedUndirectedGraph.addEdge("v3", "v4", 1);
        weightedUndirectedGraph.addEdge("v4", "v5", 2);

        Graph graph = new Graph(vertices);
        graph.addEdge("v1", "v2");
        graph.addEdge("v2", "v3");
        graph.addEdge("v3", "v4");
        graph.addEdge("v4", "v5");

        Graph weightedGraph = new Graph(vertices);
        weightedGraph.addEdge("v1", "v2", 3);
        weightedGraph.addEdge("v2", "v3", 4);
        weightedGraph.addEdge("v3", "v4", 1);
        weightedGraph.addEdge("v4", "v5", 2);
    }
}
