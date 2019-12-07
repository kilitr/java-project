import java.util.Set;

public class UndirectedGraph extends Graph {
    public UndirectedGraph(Set<String> verticeLabels) {
        super(verticeLabels);
    }

    @Override
    public boolean addEdge(String label1, String label2) {
        Vertex vertex1 = getVertex(label1);
        Vertex vertex2 = getVertex(label2);
        if (vertex1 == null || vertex2 == null) return false;
        vertex1.addEdge(new Edge(vertex2));
        vertex2.addEdge(new Edge(vertex1));
        return true;
    }
}
