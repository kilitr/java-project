package de.kilitr;

public class UndirectedGraph extends BaseGraph {

    public UndirectedGraph(String[] verticeLabels) {
        super();
        for (int i = 0; i < verticeLabels.length; i++) {
            this.addVertex(new Vertex(verticeLabels[i]));
        }
    }


    public boolean addEdge(String label1, String label2, int weight) {
        Vertex vertex1 = getVertex(label1);
        Vertex vertex2 = getVertex(label2);
        if (vertex1 == null || vertex2 == null) return false;
        vertex1.addEdge(new Edge(vertex2, weight));
        vertex2.addEdge(new Edge(vertex1, weight));
        return true;
    }

    public boolean addEdge(String label1, String label2) {
        return addEdge(label1, label2, 1);
    }
}
