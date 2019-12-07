package de.kilitr;

import java.util.Set;

public class Graph extends BaseGraph {

    public Graph(Set<String> verticeLabels) {
        super();
        for (String label : verticeLabels) {
            this.addVertex(new Vertex(label));
        }
    }

    public boolean addEdge(String labelSource, String labelDestination) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) return false;
        source.addEdge(new Edge(destination));
        return true;
    }
}
