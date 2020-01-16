package de.kilitr;

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
    TreeMap<String, CustomTuple> distanceAndOrigin;

    public Dijkstra(Graph g, Vertex start) {
        logger.debug("Preparing Dijkstra for start=" + start.getLabel() + " ... ");
        this.graph = g;
        this.start = start;
        pQueue = new PriorityQueue<>(new VertexComparator());
        distanceAndOrigin = new TreeMap<>(new TreeAlphaNumComp());
        for(Vertex v : graph.getVertices()) {
            distanceAndOrigin.put(v.getLabel(), new CustomTuple(Integer.MAX_VALUE, null)); // Because int has no Infinity
        }

        // Initialize distanceAndOrigin with start vertex for dijkstra
        distanceAndOrigin.put(start.getLabel(), new CustomTuple(0, start));
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
                CustomTuple entry = distanceAndOrigin.get(to.getLabel());
                int newWeight = distanceAndOrigin.get(inspectedVertex.getLabel()).getWeight() + e.getWeight();
                if (entry.getWeight() > newWeight || entry.getWeight() == Integer.MAX_VALUE) {
                    CustomTuple p = new CustomTuple(newWeight, inspectedVertex);
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
        Vertex parent = distanceAndOrigin.get(v.getLabel()).getParent();
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
            int weightV1 = distanceAndOrigin.get(v1.getLabel()).getWeight();
            int weightV2 = distanceAndOrigin.get(v2.getLabel()).getWeight();
            return Integer.compare(weightV1, weightV2);
        }
    }
}
