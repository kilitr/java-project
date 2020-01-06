package de.kilitr;

import java.util.HashMap;
import java.util.PriorityQueue;

public class UndirectedDijkstra {
    private UndirectedGraph graph;
    private HashMap<String, Integer> distances;
    private PriorityQueue<Edge> pQueue;
    private Vertex start;

    public UndirectedDijkstra(UndirectedGraph graphToExamine, Vertex start) {
        this.graph = graphToExamine;
        this.start = start;
    }

    public void execute() {
        System.out.println("Executing Dijkstra");

        // General Initialization
        distances = new HashMap<>();
        pQueue = new PriorityQueue<>();
        for(Vertex v : graph.getVertices()) {
            distances.put(v.getLabel(), -1); // TODO: Discuss whether to use -1 or Integer.MAX_VALUE
        }

        // Initialize by handling start Vertex
        distances.put(start.getLabel(), 0);
        inspect(start);

        while(pQueue.size() > 0) {
            Vertex currentVertex = pQueue.poll().getTo();
            System.out.println(currentVertex);
        }
    }

    private void inspect(Vertex v) {
        for(Edge e : v.getEdges()) {
            distances.put(e.getTo().getLabel(), e.getWeight());
            pQueue.add(e);
        }
    }
}
