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
    void addEdge_unweighted_validVertexAsParameter_onlyOneEdgeShouldExistPointedToByBothVertices() {
        try {
            ug = new UndirectedGraph(new String[]{"a", "b"});
            Edge edge = ug.addEdge("a", "b");
            Vertex a = ug.getVertex("a");
            Vertex b = ug.getVertex("b");
            assertEquals(a.getEdges(), ug.getVertex("b").getEdges());
            assertEquals(edge.getWeight(), 1);
            assertEquals(a.getTo(edge).getLabel(), "b");
            assertEquals(b.getTo(edge).getLabel(), "a");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    void addEdge_weighted_validVertexAsParameter_onlyOneEdgeShouldExistPointedToByBothVertices() {
        try {
            int weight = 11;
            ug = new UndirectedGraph(new String[]{"a", "b"});
            Edge edge = ug.addEdge("a", "b", weight);
            Vertex a = ug.getVertex("a");
            Vertex b = ug.getVertex("b");
            assertEquals(a.getEdges(), b.getEdges());
            assertEquals(edge.getWeight(), weight);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}