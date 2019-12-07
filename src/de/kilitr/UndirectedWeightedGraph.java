package de.kilitr;

import java.util.Set;

public class UndirectedWeightedGraph extends WeightedGraph {

    public UndirectedWeightedGraph(Set<String> verticeLabels) {
        super(verticeLabels);
    }

    @Override
    public boolean addEdge(String labelSource, String labelDestination, int weight) {
        Vertex vertex1 = getVertex(labelSource);
        Vertex vertex2 = getVertex(labelDestination);
        if (vertex1 == null || vertex2 == null) return false;
        vertex1.addEdge(new WeightedEdge(vertex2, weight));
        vertex2.addEdge(new WeightedEdge(vertex1, weight));
        return true;
    }
}
