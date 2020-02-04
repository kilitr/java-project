package de.kilitr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Describes a single instance of the vertices of a graph.
 */
public class Node {

    private final String label;
    private final Set<Edge> edges;

    /**
     * Creates new node with custom label / id.
     *
     * @param label Label or ID of that node.
     */
    public Node(String label) {
        this.label = label;
        edges = new HashSet<>();
    }

    /**
     * provides the label of this node.
     *
     * @return The custom label of this node.
     */
    public String getLabel() {
        return label;
    }

    /**
     * adds an the specified edge to this node.
     *
     * @param edge Edge to add to this node. (This node will be the source of that edge)
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * get a list of all edges.
     * @return All edges of this node in an ArrayList.
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }


    /**
     * provides the weight of the Edge to the specified node.
     *
     * @param target the target node
     * @return the weight of the edge to the given node.
     * @throws Exception when no edge connects the source and target node.
     */
    public int getWeightTo(Node target) throws Exception {
        for (Edge e : this.getEdges()) {
            if (e.getTo() == target)
                return e.getWeight();
        }
        throw new Exception("No Edge to node " + target.getLabel());
    }

    /**
     * provides a list of vertices that are connected with this node.
     *
     * @return list of vertices connected to this node.
     */
    public List<Node> getNeighbours() {
        List<Node> neighbours = new ArrayList<>();
        for (Edge e : edges) {
            neighbours.add(e.getTo());
        }
        return neighbours;
    }

    /**
     * generates a String describing this Object.
     *
     * @return the label of this node.
     */
    @Override
    public String toString() {
        return this.getLabel();
    }
}