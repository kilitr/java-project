package de.kilitr;

/**
 * An implementation of the data structure Graph (here a undirected Graph). Can be both weighted and unweighted with
 * this implementation.
 * <p>
 * Unweighted means, that all Edges by default get <i>weight = 1</i> assigned.
 * </p>
 */
public class UndirectedGraph extends Graph {

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
     * @return true, if edge was successfully added, otherwise false.
     */
    public Edge addEdge(String label1, String label2, int weight) {
        Vertex vertex1 = getVertex(label1);
        Vertex vertex2 = getVertex(label2);
        if (vertex1 == null || vertex2 == null) return null;//TODO: throw new Exception();
        Edge e = new Edge(vertex1 ,vertex2, weight);
        vertex1.addEdge(e);
        vertex2.addEdge(e);
        return e;
    }

    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges()/2;
    }
}
