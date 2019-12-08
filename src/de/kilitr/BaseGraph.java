package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

interface IBaseGraph {
    // Weighted Graph
    boolean addEdge(String from, String to, int weight);

    // Unweighted Graph
    boolean addEdge(String from, String to);
    // Vertex getVertex(String label);
    // TODO: List<Edge> getShortestPath(Vertex src, Vertex dest);
}

public abstract class BaseGraph implements IBaseGraph {
    private Set<Vertex> vertices;

    public BaseGraph() {
        vertices = new HashSet<>();
    }

    private ArrayList<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    protected boolean addVertex(Vertex vertex) {
        return vertices.add(vertex);
    }

    public Vertex getVertex(String label) {
        List<Vertex> vertices = getVertices();
        for (Vertex vert : vertices) {
            if (vert.getLabel().equals(label)) {
                return vert;
            }
        }
        return null;
    }
}
