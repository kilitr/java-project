package de.kilitr;

import javafx.util.Pair;

import java.util.*;

public class Dijkstra {
    Graph graph;
    Vertex start;
    PriorityQueue<Vertex> pQueue;
    TreeMap<String, Pair<Integer, String>> distanceAndOrigin;

    public Dijkstra(Graph g, Vertex start) {
        System.out.print("Loading Dijkstra for start=" + start.getLabel() + " ... ");
        this.graph = g;
        this.start = start;
        pQueue = new PriorityQueue<>(new VertexComparator());
        distanceAndOrigin = new TreeMap<>(new TreeAlphaNumComp());
        for(Vertex v : graph.getVertices()) {
            distanceAndOrigin.put(v.getLabel(), new Pair<>(Integer.MAX_VALUE, null)); // Because int has no Infinity
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
        System.out.println("done");
        System.out.println(distanceAndOrigin);
    }

    private void inspect(Vertex inspectedVertex) {
            for (Edge e : inspectedVertex.getEdges()) {
                Vertex to;
                if(graph instanceof UndirectedGraph) to = inspectedVertex.getUndirectedTo(e);
                else to = e.getTo();
                Pair<Integer, String> entry = distanceAndOrigin.get(to.getLabel());
                int newWeight = distanceAndOrigin.get(inspectedVertex.getLabel()).getKey() + e.getWeight();
                if(entry.getKey() > newWeight || entry.getKey() == Integer.MAX_VALUE) {
                    Pair <Integer, String> p = new Pair<>(newWeight, inspectedVertex.getLabel());
                    distanceAndOrigin.put(to.getLabel(), p);
                    pQueue.add(to);
                }

            }
    }

    public LinkedList<Edge> getPathTo(Vertex v) {
        if(distanceAndOrigin.get(v.getLabel()).getKey() != Integer.MAX_VALUE) {
            LinkedList<Edge> rawPath = new LinkedList<>();
            Vertex parent;
            while (distanceAndOrigin.get(v.getLabel()).getValue() != start.getLabel()) {
                try {
                    parent = graph.getVertex(distanceAndOrigin.get(v.getLabel()).getValue());
                    Edge part;
                    if(graph instanceof UndirectedGraph){
                        part = v.getNormalizedUndirectedEdgeTo(parent);
                    }
                    else if(graph instanceof DirectedGraph){
                        part = v.getDirectedEdgeFrom(parent);
                    }
                    else{
                        throw new Exception("Dijkstra was applied to object of unknown class.");
                    }
                    rawPath.add(0, part);
                    v = parent;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            try {
                parent = graph.getVertex(distanceAndOrigin.get(v.getLabel()).getValue());
                Edge part;
                if(graph instanceof UndirectedGraph) part = v.getNormalizedUndirectedEdgeTo(parent);
                else if(graph instanceof DirectedGraph) part = v.getDirectedEdgeFrom(parent);
                else throw new Exception("Dijkstra was applied to object of unknown class.");
                rawPath.add(0, part);
                v = parent;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return rawPath;//return new Path(rawPath);
        }
        return null; //TODO: or exception ?
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
