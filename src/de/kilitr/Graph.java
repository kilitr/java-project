package de.kilitr;

import de.kilitr.exceptions.DuplicateVertexException;
import de.kilitr.exceptions.VertexNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

/**
 * The basic "exposed" functionality, that every inheriting member of this hierarchy must have.
 */
@SuppressWarnings("unused")
interface IGraph {
    /**
     * Provides a list of vertices contained.
     *
     * @return a list of vertices contained in the Graph.
     */
    TreeSet<Vertex> getVertices();

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add.
     */
    void addVertex(Vertex vertex);

    /**
     * generates a TreeSet ordered by the TreeStringAlphaNumComp containing all labels of the vertices in the graph.
     *
     * @return TreeSet containing all labels of the vertices in the graph.
     */
    TreeSet<String> getVertexLabels();

    /**
     * Get a vertex object by the label / id.
     *
     * @param label the label (CLI = id) of the desired vertex.
     * @return Vertex if vertex with the given label exists, otherwise null.
     * @throws VertexNotFoundException thrown, when the Vertex could not be found.
     */
    Vertex getVertex(String label) throws VertexNotFoundException;

    /**
     * Provides the amount of vertices contained.
     *
     * @return amount of vertices contained in the graph.
     */
    int getNumberOfVertices();

    /**
     * generates a TreeSet ordered by the TreeStringAlphaNumComp containing all labels of the edges in the graph.
     *
     * @return TreeSet containing all labels of the edges in the graph.
     */
    TreeSet<String> getEdgeLabels();

    /**
     * Provides the amount of edges contained.
     *
     * @return amount of edges contained in the graph.
     */
    int getNumberOfEdges();

    /**
     * wheter this graph is connected or not.
     *
     * @return boolean value, whether the graph is connected or not.
     */
    boolean isConnected();
}

/**
 * The baseclass for all following Graph data structures, that contains the functionality that applies to both, directed
 * and undirected Graphs.
 */
public abstract class Graph implements IGraph {

    protected final TreeSet<Vertex> vertices;

    /**
     * Creates a prefilled graph from a list of vertex labels. Will contain no Edges, just Vertices with the provided
     * Strings as label.
     *
     * @param verticeLabels An array of vertex labels.
     * @throws DuplicateVertexException Gets thrown, when there is a duplicate in the list of labels.
     */
    protected Graph(String[] verticeLabels) throws DuplicateVertexException {
        this.vertices = new TreeSet<>(new TreeVertexAlphaNumComp());
        for (String verticeLabel : verticeLabels) {
            try {
                if (this.getVertex(verticeLabel) != null) {
                    throw new DuplicateVertexException("Cannot create graph! - This Graph contains at least two different Vertices, that were given the same name.");
                }
            } catch (VertexNotFoundException e) {
                // To be expected and nothing to worry about due to the fact that we are hoping not to find a Vertex here.
            }
            this.addVertex(new Vertex(verticeLabel));
        }
    }

    /**
     * Provides a list of vertices contained.
     *
     * @return a list of vertices contained in the Graph.
     */
    public TreeSet<Vertex> getVertices() {
        return new TreeSet<>(vertices);
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add.
     */
    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }


    /**
     * Get a vertex object by the label / id.
     *
     * @param label the label (CLI = id) of the desired vertex.
     * @return Vertex if vertex with the given label exists, otherwise null.
     */
    public Vertex getVertex(String label) throws VertexNotFoundException {
        TreeSet<Vertex> vertices = getVertices();
        for (Vertex vert : vertices) {
            if (vert.getLabel().equals(label)) {
                return vert;
            }
        }
        throw new VertexNotFoundException("Vertex '" + label + "' does not exist.");
    }

    /**
     * Provides the amount of vertices contained.
     *
     * @return amount of vertices contained in the graph.
     */
    public int getNumberOfVertices() {
        return vertices.size();
    }

    /**
     * Provides the amount of edges contained.
     *
     * @return amount of edges contained in the graph.
     */
    public int getNumberOfEdges() {
        int sum = 0;
        for (Vertex v : vertices) {
            sum += v.getEdges().size();
        }
        return sum;
    }


    /**
     * wheter this graph is connected or not.
     * @return boolean value, whether the graph is connected or not.
     */
    public boolean isConnected() {
        return breadthFirstSearch() == getNumberOfVertices();
    }

    private int breadthFirstSearch() {
        int visitedVertices = 0;
        Vertex current = getVertices().first();
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

    /**
     * generates a TreeSet ordered by the TreeStringAlphaNumComp containing all labels of the vertices in the graph.
     * @return TreeSet containing all labels of the vertices in the graph.
     */
    public TreeSet<String> getVertexLabels() {
        TreeSet<String> vertexLabels = new TreeSet<>(new TreeStringAlphaNumComp());
        for (Vertex v : this.getVertices()) {
            vertexLabels.add(v.getLabel());
        }
        return vertexLabels;
    }

    public abstract TreeSet<String> getEdgeLabels();
}
