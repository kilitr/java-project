import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        WeightedGraph weightedGraph = new WeightedGraph(new HashSet<>(Arrays.asList("v1", "v2", "v3", "v4", "v5")));
        weightedGraph.addEdge("v1", "v2", 1);
        weightedGraph.addEdge("v2", "v3", 2);
        weightedGraph.addEdge("v3", "v4", 3);
        weightedGraph.addEdge("v4", "v5", 1);

        UndirectedGraph undirectedGraph = new UndirectedGraph(new HashSet<>(Arrays.asList("v1", "v2", "v3", "v4", "v5")));
        undirectedGraph.addEdge("v1", "v2");
        undirectedGraph.addEdge("v2", "v3");
        undirectedGraph.addEdge("v3", "v4");
        undirectedGraph.addEdge("v4", "v5");
    }
}
