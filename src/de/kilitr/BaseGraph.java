package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseGraph {
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

    protected Vertex getVertex(String label) {
        List<Vertex> vertices = getVertices();
        for (Vertex vert : vertices) {
            if (vert.getLabel().equals(label)) {
                return vert;
            }
        }
        return null;
    }


}
