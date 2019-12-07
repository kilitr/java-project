package de.kilitr;

import java.util.Set;

public class WeightedGraph extends BaseGraph {

    public WeightedGraph(Set<String> verticeLabels) {
        super();
        for (String label : verticeLabels) {
            this.addVertex(new Vertex(label));
        }
    }

    public boolean addEdge(String labelSource, String labelDestination, int weight) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) return false;
        source.addEdge(new WeightedEdge(destination, weight));
        return true;
    }
}
