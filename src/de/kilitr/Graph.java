package de.kilitr;

import de.kilitr.exceptions.DuplicateVertexException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

interface IGraph {
    /**
     * Connects two vertices in one direction with custom weight.
     *
     * @param from   Label of vertex that this edge starts from.
     * @param to     Label of vertex that this edge leads to.
     * @param weight The custom weight of this Edge.
     */
    void addEdge(String from, String to, int weight);
}

/**
 * The baseclass for all following Graph data structures, that contains the functionality that applies to both, directed
 * and undirected Graphs.
 */
public abstract class Graph implements IGraph {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    protected TreeSet<Vertex> vertices;

    protected Graph(String[] verticeLabels) throws DuplicateVertexException {
        this.vertices = new TreeSet<>(new TreeAlphaNumComp());
        for (String verticeLabel : verticeLabels) {
            if (this.getVertex(verticeLabel) != null) {
                throw new DuplicateVertexException("Cannot create graph! - This Graph contains at least two different Vertices, that were given the same name.");
            }
            this.addVertex(new Vertex(verticeLabel));
        }
    }

    /**
     * Provides a list of vertices contained.
     *
     * @return a list of vertices contained in the Graph.
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

    /**
     * Provides the amount of vertices contained.
     *
     * @return amount of vertices contained in the graph.
     */
    public int getNumberOfVertices() {
        return vertices.size();
    }

    protected int getNumberOfEdges() {
        int sum = 0;
        for(Vertex v : vertices) {
            sum += v.getEdges().size();
        }
        return sum;
    }


    /**
     * wheter this graph is connected or not.
     *
     * @return boolean value, whether the graph is connected or not.
     */
    public boolean isConnected() {
        return breadthFirstSearch() == getNumberOfVertices();
    }

    private int breadthFirstSearch() {
        int visitedVertices = 0;
        Vertex current = getVertices().get(0);
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex v : vertices) {
            visited.put(v, false);
        }

        visited.put(current, true);
        visitedVertices++;
        queue.add(current);

        while (!queue.isEmpty()) {
            current = queue.poll();
            for (Vertex v : current.getNeighbours()) {
                if (!visited.get(v)) {
                    visited.put(v, true);
                    visitedVertices++;
                    queue.add(v);
                }
            }
        }
        return visitedVertices;
    }

}
