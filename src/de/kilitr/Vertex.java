package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class describing the vertices of a graph.
 */
public class Vertex {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    private String label;
    private Set<Edge> edges;

    /**
     * Creates new vertex with custom label / id.
     *
     * @param label Label or ID of that vertex.
     */
    public Vertex(String label) {
        this.label = label;
        edges = new HashSet<>();
    }

    /**
     * @return The custom label of this vertex.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param edge Edge to add to this vertex. (This vertex will be the source of that edge)
     * @return True if the Edge was not already present and is new, else it returns False if the edge is already present
     *         in the vertex.
     */
    public boolean addEdge(Edge edge) {
        return edges.add(edge);
    }

    /**
     * @return All edges of this vertex in an ArrayList.
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Only for usage in context with undirected graphs
     *
     * @param e
     * @return Vertex, that e points to
     */
    public Vertex getUndirectedTo(Edge e) {
        return e.getTo() == this ? e.getFrom() : e.getTo();
    }


    public int getWeightTo(Vertex v) {
        for (Edge e : this.getEdges()) {
            if (e.getTo() == v)
                return e.getWeight();
        }
        return -1000;
    }

    public List<Vertex> getNeighbours() {
        List<Vertex> neighbours = new ArrayList<>();
        for (Edge e : edges) {
            neighbours.add(e.getTo());
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }
}