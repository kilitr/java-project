import java.util.List;
import java.util.Set;

class WeightedGraph extends BaseGraph {
    public WeightedGraph(Set<String> verticeLabels) {
        super();
        for (String label : verticeLabels) {
            this.addVertex(new Vertex(label));
        }
    }

    private Vertex getVertex(String label) {
        List<Vertex> vertices = getVertices();
        for (Vertex vert : vertices) {
            if (vert.getLabel().equals(label)) {
                return vert;
            }
        }
        return null;
    }

    public boolean addEdge(String labelSource, String labelDestination, int weight) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) return false;
        source.addEdge(new WeightedEdge(destination, weight));
        return true;
    }
}
