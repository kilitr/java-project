package de.kilitr;

import de.kilitr.exceptions.DuplicateNodeException;
import de.kilitr.exceptions.NodeNotFoundException;

import java.util.*;

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
    TreeSet<Node> getNodes();

    /**
     * Adds a node to the graph.
     *
     * @param node the node to add.
     */
    void addNode(Node node);

    /**
     * generates a TreeSet ordered by the TreeStringAlphaNumComp containing all labels of the vertices in the graph.
     *
     * @return TreeSet containing all labels of the vertices in the graph.
     */
    TreeSet<String> getNodeLabels();

    /**
     * Get a node object by the label / id.
     *
     * @param label the label (CLI = id) of the desired node.
     * @return node if node with the given label exists, otherwise null.
     * @throws NodeNotFoundException thrown, when the node could not be found.
     */
    Node getNode(String label) throws NodeNotFoundException;

    /**
     * Provides the amount of vertices contained.
     *
     * @return amount of vertices contained in the graph.
     */
    int getNumberOfNodes();

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
     * whether this graph is connected or not.
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

    protected final TreeSet<Node> nodes;

    /**
     * Creates a prefilled graph from a list of node labels. Will contain no Edges, just Vertices with the provided
     * Strings as label.
     *
     * @param nodeLabels An array of node labels.
     * @throws DuplicateNodeException Gets thrown, when there is a duplicate in the list of labels.
     */
    protected Graph(String[] nodeLabels) throws DuplicateNodeException {
        this.nodes = new TreeSet<>(new TreeAlphaNumNodeComparator());
        for (String nodeLabel : nodeLabels) {
            try {
                if (this.getNode(nodeLabel) != null) {
                    throw new DuplicateNodeException("Cannot create graph! - This Graph contains at least two different Vertices, that were given the same name.");
                }
            } catch (NodeNotFoundException e) {
                // To be expected and nothing to worry about due to the fact that we are hoping not to find a node here.
            }
            this.addNode(new Node(nodeLabel));
        }
    }

    /**
     * Provides a list of vertices contained.
     *
     * @return a list of vertices contained in the Graph.
     */
    public TreeSet<Node> getNodes() {
        return new TreeSet<>(nodes);
    }

    /**
     * Adds a node to the graph.
     *
     * @param node the node to add.
     */
    public void addNode(Node node) {
        nodes.add(node);
    }


    /**
     * Get a node object by the label / id.
     *
     * @param label the label (CLI = id) of the desired node.
     * @return node if node with the given label exists, otherwise null.
     */
    public Node getNode(String label) throws NodeNotFoundException {
        TreeSet<Node> nodes = getNodes();
        for (Node node : nodes) {
            if (node.getLabel().equals(label)) {
                return node;
            }
        }
        throw new NodeNotFoundException("node '" + label + "' does not exist.");
    }

    /**
     * Provides the amount of vertices contained.
     *
     * @return amount of vertices contained in the graph.
     */
    public int getNumberOfNodes() {
        return nodes.size();
    }

    /**
     * Provides the amount of edges contained.
     *
     * @return amount of edges contained in the graph.
     */
    public int getNumberOfEdges() {
        int sum = 0;
        for (Node node : nodes) {
            sum += node.getEdges().size();
        }
        return sum;
    }


    /**
     * whether this graph is connected or not.
     *
     * @return boolean value, whether the graph is connected or not.
     */
    public boolean isConnected() {
        return breadthFirstSearch() == getNumberOfNodes();
    }

    private int breadthFirstSearch() {
        int visitedVertices = 0;
        Node current = getNodes().first();
        HashMap<Node, Boolean> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        for (Node node : nodes) {
            visited.put(node, false);
        }

        visited.put(current, true);
        visitedVertices++;
        queue.add(current);

        while (!queue.isEmpty()) {
            current = queue.poll();
            for (Node node : current.getNeighbours()) {
                if (!visited.get(node)) {
                    visited.put(node, true);
                    visitedVertices++;
                    queue.add(node);
                }
            }
        }
        return visitedVertices;
    }

    /**
     * generates a TreeSet ordered by the TreeStringAlphaNumComp containing all labels of the vertices in the graph.
     *
     * @return TreeSet containing all labels of the vertices in the graph.
     */
    public TreeSet<String> getNodeLabels() {
        TreeSet<String> nodeLabels = new TreeSet<>(new TreeAlphaNumStringComparator());
        for (Node node : this.getNodes()) {
            nodeLabels.add(node.getLabel());
        }
        return nodeLabels;
    }

    public abstract TreeSet<String> getEdgeLabels();

    public abstract ArrayList<Edge> getEdges();
}
