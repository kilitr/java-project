package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Describes a single instance of the vertices of a graph.
 */
public class Vertex {

    private final String label;
    private final Set<Edge> edges;

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
     * provides the label of this vertex.
     * @return The custom label of this vertex.
     */
    public String getLabel() {
        return label;
    }

    /**
     * adds an the specified edge to this vertex.
     *
     * @param edge Edge to add to this vertex. (This vertex will be the source of that edge)
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * get a list of all edges.
     * @return All edges of this vertex in an ArrayList.
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }


    /**
     * provides the weight of the Edge to the specified vertex.
     * @param target the target vertex
     * @return the weight of the edge to the given vertex.
     * @throws Exception when no edge connects the source and target vertex.
     */
    public int getWeightTo(Vertex target) throws Exception {
        for (Edge e : this.getEdges()) {
            if (e.getTo() == target)
                return e.getWeight();
        }
        throw new Exception("No Edge to vertex " + target.getLabel());
    }

    /**
     * provides a list of vertices that are connected with this vertex.
     *
     * @return list of vertices connected to this vertex.
     */
    public List<Vertex> getNeighbours() {
        List<Vertex> neighbours = new ArrayList<>();
        for (Edge e : edges) {
            neighbours.add(e.getTo());
        }
        return neighbours;
    }

    /**
     * generates a String describing this Object.
     *
     * @return the label of this vertex.
     */
    @Override
    public String toString() {
        return this.getLabel();
    }
}