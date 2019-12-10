package de.kilitr.test;

import de.kilitr.UndirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UndirectedGraphTest {
    private UndirectedGraph ug;

    @Test
    void addEdge_unweighted_validVertexAsParameter_ShouldBeAddedToBothVertices() {
        try {
            ug = new UndirectedGraph(new String[]{"a", "b"});
            ug.addEdge("a", "b");
            assertEquals(ug.getVertex("a").getEdges().get(0).getTo(), ug.getVertex("b"));
            assertEquals(ug.getVertex("a").getEdges().get(0).getWeight(), 1);
            assertEquals(ug.getVertex("b").getEdges().get(0).getTo(), ug.getVertex("a"));
            assertEquals(ug.getVertex("b").getEdges().get(0).getWeight(), 1);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    void addEdge_weighted_validVertexAsParameter_ShouldBeAddedToBothVertices() {
        try {
            int weight = 11;
            ug = new UndirectedGraph(new String[]{"a", "b"});
            ug.addEdge("a", "b", weight);
            assertEquals(ug.getVertex("a").getEdges().get(0).getTo(), ug.getVertex("b"));
            assertEquals(ug.getVertex("a").getEdges().get(0).getWeight(), weight);
            assertEquals(ug.getVertex("b").getEdges().get(0).getTo(), ug.getVertex("a"));
            assertEquals(ug.getVertex("b").getEdges().get(0).getWeight(), weight);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}