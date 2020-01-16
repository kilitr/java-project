package de.kilitr;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Dijkstra {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    Graph graph;
    Vertex start;
    PriorityQueue<Vertex> pQueue;
    TreeMap<String, Pair<Integer, String>> distanceAndOrigin;

    public Dijkstra(Graph g, Vertex start) {
        logger.debug("Preparing Dijkstra for start=" + start.getLabel() + " ... ");
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
        logger.debug("executing Dijkstra");
        while(!pQueue.isEmpty()) {
            inspect(pQueue.poll());
        }
    }

    private void inspect(Vertex inspectedVertex) {
            for (Edge e : inspectedVertex.getEdges()) {
                Vertex to = e.getTo();
                Pair<Integer, String> entry = distanceAndOrigin.get(to.getLabel());
                int newWeight = distanceAndOrigin.get(inspectedVertex.getLabel()).getKey() + e.getWeight();
                if (entry.getKey() > newWeight || entry.getKey() == Integer.MAX_VALUE) {
                    Pair<Integer, String> p = new Pair<>(newWeight, inspectedVertex.getLabel());
                    distanceAndOrigin.put(to.getLabel(), p);
                    pQueue.add(to);
                }

            }
    }

    public LinkedList<Edge> getPathTo(Vertex v) {
        LinkedList<Edge> path = new LinkedList<>();
        Edge edgeToParent = getEdgeTo(v);
        path.add(0, edgeToParent);
        assert edgeToParent != null; // TODO: prettier solution to avoid NullPointerException
        while (edgeToParent.getFrom() != start) {
            edgeToParent = getEdgeTo(edgeToParent.getFrom());
            path.add(0, edgeToParent);
        }
        return path;
    }

    /**
     * If there exists a path to v, this will return the optimal (according to Dijkstra) Edge to v.
     *
     * @param v is the destination Vertex.
     * @return Edge from the Dijkstra-Graph based parent to v.
     */
    private Edge getEdgeTo(Vertex v) {
        String parentLabel = distanceAndOrigin.get(v.getLabel()).getValue();
        Vertex parent = graph.getVertex(parentLabel);
        for (Edge e : parent.getEdges()) {
            if (e.getTo() == v) {
                return e;
            }
        }
        return null;
    }

    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            int weightV1 = distanceAndOrigin.get(v1.getLabel()).getKey();
            int weightV2 = distanceAndOrigin.get(v2.getLabel()).getKey();
            return Integer.compare(weightV1, weightV2);
        }
    }
}
