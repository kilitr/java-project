package de.kilitr;


import java.util.*;

public class Dijkstra2 {
    private Graph graph;
    private Vertex start;
    private HashMap<Vertex, List<Integer>> distance;
    private HashMap<Vertex, List<Vertex>> predecessor;
    private Queue<Vertex> q;


    public Dijkstra2(Graph g, Vertex start) {
        this.distance = new HashMap<>();
        this.predecessor = new HashMap<>();
        this.q = new PriorityQueue<>(new VertexComparator());
        this.graph = g;
        this.start = start;
        for (Vertex v : this.graph.getVertices()) {
            distance.put(v, List.of(Integer.MAX_VALUE));
            predecessor.put(v, null);
        }
        distance.put(this.start, List.of(0));
        q.addAll(this.graph.getVertices());
    }

    public HashMap<Vertex, List<Vertex>> execute() {
        while (!q.isEmpty()) {
            Vertex u = q.poll();
            for (Vertex v : u.getNeighbours()) {
                if (q.contains(v)) {
                    update(u, v);
                }
            }
        }
        return predecessor;
    }

    public LinkedList<Vertex> createShortestPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        path.add(target);
        Vertex u = target;
        while (predecessor.get(u) != null) { // Predecessor of start is null
            u = predecessor.get(u).get(0);
            path.add(0, u);
        }
        return path;
    }

    private void update(Vertex u, Vertex v) {
        int alternative = distance.get(u).get(0) + u.getWeightTo(v);
        if (alternative < distance.get(v).get(0)) {
            distance.put(v, List.of(alternative));
            predecessor.put(v, List.of(u));
        } else if (alternative == distance.get(v).get(0)) {
            // würde das dafür sorgen, dass 2 kürzeste Wege gefunden werden?
            List<Integer> multipleDistances = new ArrayList<>();
            multipleDistances.add(distance.get(v).get(0));
            multipleDistances.add(alternative);
            List<Vertex> multiplePredecessors = new ArrayList<>();
            multiplePredecessors.add(predecessor.get(v).get(0));
            multiplePredecessors.add(u);
            distance.put(v, multipleDistances);
            predecessor.put(v, multiplePredecessors);
        }
    }

    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            int weightV1 = distance.get(v1).get(0);
            int weightV2 = distance.get(v2).get(0);
            return Integer.compare(weightV1, weightV2);
        }
    }
}
