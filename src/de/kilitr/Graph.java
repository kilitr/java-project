package de.kilitr;

import java.util.*;

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
    // Vertex getVertex(String label);
    // TODO: List<Edge> getShortestPath(Vertex src, Vertex dest);
}

/**
 * The baseclass for all following Graph data structures, that contains the functionality that applies to both, directed
 * and undirected Graphs.
 */
public abstract class Graph implements IGraph {
    protected Set<Vertex> vertices;

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

    private ArrayList<Vertex> getVertices() {
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
