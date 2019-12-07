import java.util.List;
import java.util.Set;

public class Graph extends BaseGraph {

    public Graph(Set<String> verticeLabels) {
        super();
        for (String label : verticeLabels) {
            this.addVertex(new Vertex(label));
        }
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

    public boolean addEdge(String labelSource, String labelDestination) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) return false;
        source.addEdge(new Edge(destination));
        return true;
    }
}
