package de.kilitr;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Dijkstra {
    Graph graph;
    Vertex start;
    ArrayList<Vertex> visited;
    PriorityQueue<Vertex> pQueue;
    TreeMap<String, Pair<Integer, String>> distanceAndOrigin;

    public Dijkstra(Graph g, Vertex start) {
        this.graph = g;
        this.start = start;
        visited = new ArrayList<>();
        pQueue = new PriorityQueue<>(new VertexComparator());
        distanceAndOrigin = new TreeMap<>(new TreeAlphaNumComp());
        for(Vertex v : graph.getVertices()) {
            distanceAndOrigin.put(v.getLabel(), new Pair<>(-1, null));
        }

        // Initialize distanceAndOrigin with start vertex for dijkstra
        distanceAndOrigin.put(start.getLabel(), new Pair<>(0, start.getLabel()));
        inspect(start);
    }

    public void execute() {
        while(!pQueue.isEmpty()) {
            inspect(pQueue.poll());
        }
        formatResults();
    }

    private void inspect(Vertex inspectedVertex) {
        if(!visited.contains(inspectedVertex)) {
            for (Edge e : inspectedVertex.getEdges()) {
                Vertex to = inspectedVertex.getTo(e);
                Pair<Integer, String> entry = distanceAndOrigin.get(to.getLabel());
                if(entry.getKey() > e.getWeight() || entry.getKey() == -1) {
                    Pair <Integer, String> p = new Pair<>(e.getWeight(), inspectedVertex.getLabel());
                    if(reachesStart(p, to)) {
                        distanceAndOrigin.put(to.getLabel(), p);
                    }
                }
                pQueue.add(to);
            }
            visited.add(inspectedVertex);
        }
    }

    private void formatResults() {
        System.out.println(distanceAndOrigin);
        //TODO: create TreeMap<String, Pair<Integer, LinkedList<Edge>>> for easier output of data
    }

    private boolean reachesStart(Pair<Integer, String> pairToCheck, Vertex currentVertex) {
        //System.out.println("Current Vertex = " + v.getLabel());
        //System.out.println("Parent Vertex = " + p.getValue());
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
            if(v1.getEdges().isEmpty()) return -1;
            if(v2.getEdges().isEmpty()) return 1;
            int thisMinWeight = v1.getMinEdge().getWeight();
            int vertexMinWeight = v2.getMinEdge().getWeight();
            return Integer.compare(thisMinWeight, vertexMinWeight);
        }
    }
}
