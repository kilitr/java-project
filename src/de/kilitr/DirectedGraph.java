package de.kilitr;

/**
 * An implementation of the data structure Graph (here a directed Graph). Can be both weighted and unweighted with this
 * implementation.
 * <p>
 * Unweighted means, that all Edges by default get <i>weight = 1</i> assigned.
 * </p>
 */
public class DirectedGraph extends Graph {

    /**
     * @param verticeLabels An array, containing all ID's / labels of the vertices, that should be contained in
     *                      the Graph.
     */
    public DirectedGraph(String[] verticeLabels) throws Exception {
        super(verticeLabels);
    }

    /**
     * Connects two vertices in one direction with custom weight.
     *
     * @param labelSource      label of vertex that this edge starts from.
     * @param labelDestination label of vertex that this edge leads to.
     * @param weight           The custom weight of this Edge.
     * @return true, if edge was successfully added, otherwise false.
     */
    public Edge addEdge(String labelSource, String labelDestination, int weight) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) ;// TODO: throw new Exception("123");
        Edge e = new Edge(source, destination, weight);
        source.addEdge(e);
        return e;
    }
}
