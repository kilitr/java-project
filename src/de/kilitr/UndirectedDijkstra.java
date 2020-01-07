package de.kilitr;

import java.util.*;

public class UndirectedDijkstra {
    private UndirectedGraph graph;
    private TreeMap<String, Integer> distances;
    private PriorityQueue<Edge> pQueue;
    private ArrayList<Edge> visited;
    private Vertex start;

    public UndirectedDijkstra(UndirectedGraph graphToExamine, Vertex start) {
        this.graph = graphToExamine;
        this.start = start;
    }

    public void execute() {
        System.out.println("Executing Dijkstra");

        // General Initialization
        distances = new TreeMap<>(new TreeAlphaNumComp());
        pQueue = new PriorityQueue<>();
        visited = new ArrayList<Edge>();
        for(Vertex v : graph.getVertices()) {
            distances.put(v.getLabel(), -1); // TODO: Discuss whether to use -1 or Integer.MAX_VALUE
        }

        // Initialize by handling start Vertex
        distances.put(start.getLabel(), 0);
        inspect(start);

        while(pQueue.size() > 0) {
            Vertex currentVertex = pQueue.poll().getTo();
            inspect(currentVertex);
        }
    }

    private void inspect(Vertex v) {
        for (Edge e : v.getEdges()) {
            if(!visited.contains(e)) {
                int newWeight = e.getWeight() + distances.get(v.getLabel());
                int oldWeight = distances.get(e.getTo().getLabel());
                if (oldWeight == -1) oldWeight = Integer.MAX_VALUE;
                distances.put(e.getTo().getLabel(), Integer.min(newWeight, oldWeight));
                pQueue.add(e);
                visited.add(e);
            }
        }
    }

    /**
     * TODO: Create JavaDoc
     * @return
     */
    public TreeMap<String, Integer> getDistances() {
        return distances;
    }
}
