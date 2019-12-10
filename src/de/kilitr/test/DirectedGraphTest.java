package de.kilitr.test;

import de.kilitr.DirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DirectedGraphTest {
    private DirectedGraph dg;

    @Test
    void addEdge_unweighted_validVertexAsParameter_ShouldBeAddedToSourceVertex() {
        try {
            dg = new DirectedGraph(new String[]{"a", "b"});
            dg.addEdge("a", "b");
            assertEquals(dg.getVertex("a").getEdges().get(0).getTo(), dg.getVertex("b"));
            assertEquals(dg.getVertex("a").getEdges().get(0).getWeight(), 1);
            assertEquals(dg.getVertex("b").getEdges().size(), 0);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    void addEdge_weighted_validVertexAsParameter_ShouldBeAddedToSourceVertex() {
        try {
            int weight = 11;
            dg = new DirectedGraph(new String[]{"a", "b"});
            dg.addEdge("a", "b", weight);
            assertEquals(dg.getVertex("a").getEdges().get(0).getTo(), dg.getVertex("b"));
            assertEquals(dg.getVertex("a").getEdges().get(0).getWeight(), weight);
            assertEquals(dg.getVertex("b").getEdges().size(), 0);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}