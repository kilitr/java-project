package de.kilitr;

import javafx.util.Pair;

import java.util.*;

public class Dijkstra {
    Graph graph;
    Vertex start;
    ArrayList<Vertex> visited;
    PriorityQueue<Vertex> pQueue;
    TreeMap<String, Pair<Integer, String>> distanceAndOrigin;
    TreeMap<String, Pair<Integer, LinkedList<Edge>>> totalDistanceAndPath;

    public Dijkstra(Graph g, Vertex start) {
        System.out.print("Loading Dijkstra for start=" + start.getLabel() + " ... ");
        this.graph = g;
        this.start = start;
        visited = new ArrayList<>();
        pQueue = new PriorityQueue<>(new VertexComparator());
        distanceAndOrigin = new TreeMap<>(new TreeAlphaNumComp());
        totalDistanceAndPath = new TreeMap<>(new TreeAlphaNumComp());
        for(Vertex v : graph.getVertices()) {
            distanceAndOrigin.put(v.getLabel(), new Pair<>(-1, null));
            totalDistanceAndPath.put(v.getLabel(), null);
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
        System.out.print("formatting ... ");
        for(Vertex v : graph.getVertices()) {
            calculateTotalCostAndPath(v);
        }
        System.out.println("done!");
    }

    private void calculateTotalCostAndPath(Vertex destination) {
        if(destination == start) {
            totalDistanceAndPath.put(start.getLabel(), new Pair<>(0, null));
            return;
        }

        Vertex parent = graph.getVertex(distanceAndOrigin.get(destination.getLabel()).getValue());
        if(parent == null){
            System.out.print("not reachable");
            return;
        }

        // Resolve path and calculate cost
        LinkedList<Edge> path = new LinkedList<>();
        int weight = 0;
        try {
            Edge partOfPath = destination.getEdgeFrom(parent);
            path.add(0, partOfPath);
            weight += partOfPath.getWeight();
            for(Vertex current = parent; current != start; current = parent) {
                parent = graph.getVertex(distanceAndOrigin.get(current.getLabel()).getValue());
                partOfPath = current.getEdgeFrom(parent);
                path.add(0, partOfPath); // Add in front, so we don't have to reverse ll
                weight += partOfPath.getWeight();
            }
            totalDistanceAndPath.put(destination.getLabel(), new Pair<>(weight, path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    /**
     * @param v
     * @return The path from the starting Vertex to the Vertex v in Form of a LinkedList of Edges
     */
    public LinkedList<Edge> getPathTo(Vertex v) {
        return totalDistanceAndPath.get(v.getLabel()).getValue();
    }

    /**
     * @param v
     * @return The weight / distance from the starting Vertex to the Vertex v
     */
    public int getWeightTo(Vertex v) {
        return totalDistanceAndPath.get(v.getLabel()).getKey();
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
