package de.kilitr;

import de.kilitr.exceptions.DuplicateNodeException;
import de.kilitr.exceptions.NodeNotFoundException;

import java.util.TreeSet;

/**
 * An implementation of the data structure Graph (here a undirected Graph).
 * <p>
 * Can be both weighted and unweighted with this implementation.
 * Unweighted means, that all Edges by default get <i>weight = 1</i> assigned.
 * </p>
 */
public class UndirectedGraph extends Graph {


    /**
     * creates the Undirected Graph utilizing the constructor of the abstract class Graph.
     *
     * @param verticeLabels An array, containing all ID's / labels of the vertices, that should be contained in
     *                      the Graph.
     * @throws DuplicateNodeException when trying to create a Graph with two identical node labels
     */
    public UndirectedGraph(String[] verticeLabels) throws DuplicateNodeException {
        super(verticeLabels);
    }


    /**
     * Connects two vertices in both directions with custom weight.
     *
     * @param label1 Label of first involved node. (order does not matter)
     * @param label2 Label of second involved node. (order does not matter)
     * @param weight The custom weight of this Edge.
     */
    public void addEdge(String label1, String label2, int weight) {
        Node node1 = null;
        Node node2 = null;
        try {
            node1 = getNode(label1);
            node2 = getNode(label2);
        } catch (NodeNotFoundException e) {
            e.printStackTrace();
        }
        if (node1 == null || node2 == null) return;//TODO: throw new Exception();
        Edge e = new Edge(node1, node2, weight);
        node1.addEdge(e);
        e = new Edge(node2, node1, weight);
        node2.addEdge(e);
    }

    /**
     * provides the amount of Edges contained.
     *
     * @return the amount of Edges contained.
     */
    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges()/2;
    }

    public TreeSet<String> getEdgeLabels() {
        TreeSet<String> edgeLabels = new TreeSet<>(new TreeStringAlphaNumComp());
        for (Node node : this.getNodes()) {
            for (Edge edge : node.getEdges()) {
                // only add, an edge once.
                if (!edgeLabels.contains(edge.reversed().toString())) {
                    edgeLabels.add(edge.toString());
                }
            }
        }
        return edgeLabels;
    }
}
