package de.kilitr;

import javafx.util.Pair;

import java.util.*;

public class Dijkstra {
    Graph graph;
    Vertex start;
    ArrayList<Vertex> visited;
    PriorityQueue<Vertex> pQueue;
    TreeMap<String, Pair<Integer, String>> distanceAndOrigin;

    public Dijkstra(Graph g, Vertex start) {
        System.out.print("Loading Dijkstra for start=" + start.getLabel() + " ... ");
        this.graph = g;
        this.start = start;
        visited = new ArrayList<>();
        pQueue = new PriorityQueue<>(new VertexComparator());
        distanceAndOrigin = new TreeMap<>(new TreeAlphaNumComp());
        for(Vertex v : graph.getVertices()) {
            distanceAndOrigin.put(v.getLabel(), new Pair<>(Integer.MAX_VALUE, null));
        }

        // Initialize distanceAndOrigin with start vertex for dijkstra
        distanceAndOrigin.put(start.getLabel(), new Pair<>(0, start.getLabel()));
        inspect(start);
    }

    public void execute() {
        System.out.print("executing ... ");
        while(!pQueue.isEmpty()) {
            inspect(pQueue.poll());
        }
        System.out.println(distanceAndOrigin);
    }

    private void inspect(Vertex inspectedVertex) {
        if(!visited.contains(inspectedVertex)) {
            for (Edge e : inspectedVertex.getEdges()) {
                Vertex to = inspectedVertex.getTo(e);
                Pair<Integer, String> entry = distanceAndOrigin.get(to.getLabel());
                int newWeight = distanceAndOrigin.get(inspectedVertex.getLabel()).getKey() + e.getWeight();
                if(entry.getKey() > newWeight || entry.getKey() == Integer.MAX_VALUE) {
                    Pair <Integer, String> p = new Pair<>(e.getWeight(), inspectedVertex.getLabel());
                    distanceAndOrigin.put(to.getLabel(), p);
                }
                pQueue.add(to);
            }
            visited.add(inspectedVertex);
        }
    }

    private boolean reachesStart(Pair<Integer, String> pairToCheck, Vertex currentVertex) {
        Vertex parent = graph.getVertex(pairToCheck.getValue());
        if(pairToCheck.getValue() == this.start.getLabel()) return true;
        boolean interrupt = true;
        while(true) {
            if(parent == start) return true;
            if(parent == currentVertex) return false;
            parent = graph.getVertex(distanceAndOrigin.get(parent.getLabel()).getValue());
        }
    }


    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            int weightV1 = distanceAndOrigin.get(v1.getLabel()).getKey();
            int weightV2 = distanceAndOrigin.get(v2.getLabel()).getKey();
            return Integer.compare(weightV1,weightV2);
        }
    }
}
