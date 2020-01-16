package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An implementation of the data structure Graph (here a undirected Graph). Can be both weighted and unweighted with
 * this implementation.
 * <p>
 * Unweighted means, that all Edges by default get <i>weight = 1</i> assigned.
 * </p>
 */
public class UndirectedGraph extends Graph {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);


    /**
     * @param verticeLabels An array, containing all ID's / labels of the vertices, that should be contained in
     *                      the Graph.
     */
    public UndirectedGraph(String[] verticeLabels) throws Exception {
        super(verticeLabels);
    }


    /**
     * Connects two vertices in both directions with custom weight.
     *
     * @param label1 Label of first involved vertex. (order does not matter)
     * @param label2 Label of second involved vertex. (order does not matter)
     * @param weight The custom weight of this Edge.
     */
    public void addEdge(String label1, String label2, int weight) {
        Vertex vertex1 = getVertex(label1);
        Vertex vertex2 = getVertex(label2);
        if (vertex1 == null || vertex2 == null) return;//TODO: throw new Exception();
        Edge e = new Edge(vertex1 ,vertex2, weight);
        vertex1.addEdge(e);
        e = new Edge(vertex2, vertex1, weight);
        vertex2.addEdge(e);
    }

    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges()/2;
    }
}
