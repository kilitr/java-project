package de.kilitr.test;

import de.kilitr.Edge;
import de.kilitr.UndirectedGraph;
import de.kilitr.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UndirectedGraphTest {
    private UndirectedGraph ug;

    @Test
    void addEdge_validVertexAsParameter_onlyOneEdgeShouldExistPointedToByBothVertices() {
        try {
            int weight = 10;
            ug = new UndirectedGraph(new String[]{"a", "b"});
            Edge edge = ug.addEdge("a", "b", weight);
            Vertex a = ug.getVertex("a");
            Vertex b = ug.getVertex("b");
            // Both Vertices must have the same amount of Edges
            assertEquals(a.getEdges(), b.getEdges());
            // The Weight of the edge must be equal to the desired weight
            assertEquals(edge.getWeight(), weight);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}