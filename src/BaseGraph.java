import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseGraph {
    private Set<Vertex> vertices;

    public BaseGraph() {
        vertices = new HashSet<>();
    }

    protected List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    protected boolean addVertex(Vertex vertex) {
        return vertices.add(vertex);
    }
}
