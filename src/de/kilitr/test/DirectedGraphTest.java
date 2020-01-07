package de.kilitr.test;

import de.kilitr.DirectedGraph;
import de.kilitr.Edge;
import de.kilitr.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DirectedGraphTest {
    private DirectedGraph dg;

    @Test
    void addEdge_unweighted_validVertexAsParameter_ShouldBeAddedToSourceVertex() {
        try {
            dg = new DirectedGraph(new String[]{"a", "b"});
            Edge edge = dg.addEdge("a", "b");
            Vertex a = dg.getVertex("a");
            Vertex b = dg.getVertex("b");
            assertEquals(a.getTo(edge), dg.getVertex("b"));
            assertEquals(edge.getWeight(), 1);
            assertEquals(b.getEdges().size(), 0);
        } catch (Exception ex) {
            fail("Unexpected Exception");
        }
    }

    @Test
    void addEdge_weighted_validVertexAsParameter_ShouldBeAddedToSourceVertex() {
        try {
            int weight = 11;
            dg = new DirectedGraph(new String[]{"a", "b"});
            Edge edge = dg.addEdge("a", "b", weight);
            Vertex a = dg.getVertex("a");
            Vertex b = dg.getVertex("b");
            assertEquals(a.getTo(edge), dg.getVertex("b"));
            assertEquals(edge.getWeight(), weight);
            assertEquals(b.getEdges().size(), 0);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}