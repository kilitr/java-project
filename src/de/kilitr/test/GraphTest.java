package de.kilitr.test;

import de.kilitr.DirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private DirectedGraph g;

    //TODO: Move to Graph
    @Test
    public void constructor_OneLabel_CreatesGraphSuccessful() {
        try {
            g = new DirectedGraph(new String[]{"a"});
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    //TODO: Move to Graph
    @Test
    public void constructor_TwoLabelsIdentical_ExceptionThrown() {
        try {
            g = new DirectedGraph(new String[]{"a", "a"});
            fail("Constructor did'nt throw when expected.");
        } catch (Exception ex) {
            // Needs to stay clear for expected Test result!
        }
    }

    @Test
    void getVertex_ValidLabel_ReturnsVertexWithThatLabel() {
        try {
            g = new DirectedGraph(new String[]{"a"});
            assertEquals("a", g.getVertex("a").getLabel());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    void getVertex_NonExistentLabel_ReturnsVertexWithThatLabel() {
        try {
            g = new DirectedGraph(new String[]{"a"});
            assertNull(g.getVertex("b"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}