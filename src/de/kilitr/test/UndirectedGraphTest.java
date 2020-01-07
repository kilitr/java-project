package de.kilitr.test;

import de.kilitr.UndirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UndirectedGraphTest {
    private UndirectedGraph ug;

    @Test
    void addEdge_unweighted_validVertexAsParameter_onlyOneEdgeShouldExistPointedToByBothVertices() {
        try {
            ug = new UndirectedGraph(new String[]{"a", "b"});
            ug.addEdge("a", "b");
            assertEquals(ug.getVertex("a").getEdges(), ug.getVertex("b").getEdges());
            assertEquals(ug.getVertex("a").getEdges().get(0).getWeight(), 1);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    void addEdge_weighted_validVertexAsParameter_onlyOneEdgeShouldExistPointedToByBothVertices() {
        try {
            int weight = 11;
            ug = new UndirectedGraph(new String[]{"a", "b"});
            ug.addEdge("a", "b", weight);
            assertEquals(ug.getVertex("a").getEdges(), ug.getVertex("b").getEdges());
            assertEquals(ug.getVertex("a").getEdges().get(0).getWeight(), weight);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}