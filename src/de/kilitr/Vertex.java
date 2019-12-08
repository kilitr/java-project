package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex {
    private String label;
    private Set<Edge> edges;

    public Vertex(String pageObject) {
        this.label = pageObject;
        edges = new HashSet<>();
    }

    public String getLabel() {
        return label;
    }

    public boolean addEdge(Edge edge) {
        return edges.add(edge);
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }
}
