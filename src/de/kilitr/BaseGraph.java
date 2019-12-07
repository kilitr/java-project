package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

interface IBaseGraph {
    boolean addEdge(Edge e);

    Vertex getVertex(String label);

    List<Edge> getShortestPath(Vertex src, Vertex dest);
}

public abstract class BaseGraph implements IBaseGraph {
    private Set<Vertex> vertices;

    public BaseGraph() {
        vertices = new HashSet<>();
    }

    private List<Vertex> getVertices() {
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
