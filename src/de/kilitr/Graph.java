package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

interface IGraph {
    /**
     * Connects two vertices in one direction with custom weight.
     *
     * @param from   Label of vertex that this edge starts from.
     * @param to     Label of vertex that this edge leads to.
     * @param weight The custom weight of this Edge.
     * @return True, if edge was successfully added, otherwise false.
     */
    boolean addEdge(String from, String to, int weight);

    /**
     * Connects two vertices in one direction with unweighted Edge.
     * <p>
     *     Note: Sets weight of edge to 1. So will only work for unweighted graph if no Edge has been assigned a
     *     different weight!
     * </p>
     * @param from Label of vertex that this edge starts from.
     * @param to Label of vertex that this edge leads to.
     * @return True, if edge was successfully added, otherwise false.
     */
    boolean addEdge(String from, String to);
}

/**
 * The baseclass for all following Graph data structures, that contains the functionality that applies to both, directed
 * and undirected Graphs.
 */
public abstract class Graph implements IGraph {
    private Set<Vertex> vertices;

    protected Graph(String[] verticeLabels) throws Exception {
        this.vertices = new HashSet<>();
        for (String verticeLabel : verticeLabels) {
            if (this.getVertex(verticeLabel) != null) {
                //TODO: Custom Exception, when trying to create Graph with 2 or more vertices with the same name"
                throw new Exception("Trying to add multiple vertices with same label");
            }
            this.addVertex(new Vertex(verticeLabel));
        }
    }

    /**
     * TODO: Write JavaDoc
     * @return
     */
    public ArrayList<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    protected boolean addVertex(Vertex vertex) {
        return vertices.add(vertex);
    }


    /**
     * Get a vertex object by the label / id.
     * @param label the label (CLI = id) of the desired vertex.
     * @return Vertex if vertex with the given label exists, otherwise null.
     */
    public Vertex getVertex(String label) {
        List<Vertex> vertices = getVertices();
        for (Vertex vert : vertices) {
            if (vert.getLabel().equals(label)) {
                return vert;
            }
        }
        return null;
    }
}
