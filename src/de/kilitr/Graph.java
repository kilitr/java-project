package de.kilitr;

public class Graph extends BaseGraph {

    public Graph(String[] verticeLabels) {
        super();
        for (int i = 0; i < verticeLabels.length; i++) {
            this.addVertex(new Vertex(verticeLabels[i]));
        }
    }

    public boolean addEdge(String labelSource, String labelDestination, int weight) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) return false;
        source.addEdge(new Edge(destination, weight));
        return true;
    }

    public boolean addEdge(String labelSource, String labelDestination) {
        return addEdge(labelSource, labelDestination, 1);
    }
}
