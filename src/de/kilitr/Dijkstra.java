package de.kilitr;


import java.util.*;

public class Dijkstra {
    private Graph graph;
    private Vertex start;
    private HashMap<Vertex, Integer> distance;
    private HashMap<Vertex, List<Vertex>> predecessor;
    private Queue<Vertex> q;


    public Dijkstra(Graph g, Vertex start) {
        this.distance = new HashMap<>();
        this.predecessor = new HashMap<>();
        this.q = new PriorityQueue<>(new VertexComparator());
        this.graph = g;
        this.start = start;
        for (Vertex v : this.graph.getVertices()) {
            distance.put(v, Integer.MAX_VALUE);
            predecessor.put(v, null);
        }
        distance.put(this.start, 0);
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

    public Paths createAllShortestPaths(Vertex target) {
        Vertex u = target;

        int amountOfPaths = 0;
        while (predecessor.get(u) != null) {
            List<Vertex> predecessors = predecessor.get(u);
            amountOfPaths = amountOfPaths + (predecessors.size() - 1);
            u = predecessors.get(0);
        }
        amountOfPaths++;

        Paths allPaths = new Paths(distance.get(target));
        for(int i = 0; i < amountOfPaths; i++) {
            LinkedList<Vertex> path = new LinkedList<>();
            path.add(target);
            u = target;
            while (predecessor.get(u) != null) { // Predecessor of start is null
                List<Vertex> predecessors = predecessor.get(u);
                if(predecessors.size() > i) {
                    u = predecessors.get(i);
                } else {
                    u = predecessors.get(0);
                }
                path.add(0, u);
            }
            allPaths.addPath(path);
        }
        return allPaths;
    }

    private void update(Vertex u, Vertex v) {
        int alternative = distance.get(u) + u.getWeightTo(v);
        if (alternative < distance.get(v)) {
            distance.put(v, alternative);
            predecessor.put(v, List.of(u));
        } else if (alternative == distance.get(v)) {
            // würde das dafür sorgen, dass 2 kürzeste Wege gefunden werden?
            List<Vertex> multiplePredecessors = new ArrayList<>();
            multiplePredecessors.add(predecessor.get(v).get(0));
            multiplePredecessors.add(u);
            distance.put(v, alternative);
            predecessor.put(v, multiplePredecessors);
        }
    }

    public HashMap<Vertex, Integer> getDistances() {
        return distance;
    }

    public Integer getDistanceTo(Vertex v) {
        return distance.get(v);
    }

    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            int weightV1 = distance.get(v1);
            int weightV2 = distance.get(v2);
            return Integer.compare(weightV1, weightV2);
        }
    }
}
