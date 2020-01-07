package de.kilitr;

import java.util.*;


// TODO: Implement check for negative Edge costs and handle error / ignore these Edges <- Discuss
public class UndirectedDijkstra {
    private UndirectedGraph graph;
    private TreeMap<String, Integer> distances;
    private PriorityQueue<Vertex> pQueue;
    private ArrayList<Edge> visited;
    private Vertex start;

    public UndirectedDijkstra(UndirectedGraph graphToExamine, Vertex start) {
        this.graph = graphToExamine;
        this.start = start;
    }

    public void execute() {
        System.out.println("Executing Dijkstra");

        // Initializing all datastructures
        distances = new TreeMap<>(new TreeAlphaNumComp());
        pQueue = new PriorityQueue<>();
        visited = new ArrayList<Edge>();
        for(Vertex v : graph.getVertices()) {
            distances.put(v.getLabel(), -1); // TODO: Discuss whether to use -1 or Integer.MAX_VALUE
        }

        // Initialize Dijkstra by handling start Vertex
        distances.put(start.getLabel(), 0);
        inspect(start);

        // Iterating over the next Item with lowest weight in the priority queue
        while(pQueue.size() > 0) {
            Vertex currentVertex = pQueue.poll();
            inspect(currentVertex);
        }
    }

    private void inspect(Vertex v) {
        for (Edge e : v.getEdges()) {
            if(!visited.contains(e)) { // prevent from adding visited Edges to the priority queue
                int newWeight = e.getWeight() + distances.get(v.getLabel());
                int oldWeight = distances.get(e.getTo().getLabel());
                if (oldWeight == -1) oldWeight = Integer.MAX_VALUE;
                distances.put(e.getTo().getLabel(), Integer.min(newWeight, oldWeight));
                pQueue.add(v.getTo(e));
                visited.add(e);
            }
        }
    }

    /**
     * Contains cost / distance to every Vertex from the start Vertex
     * @return TreeMap with the label of the destination Vertex as key and the distance / weight as value.
     */
    public TreeMap<String, Integer> getDistances() {
        return distances;
    }
}
