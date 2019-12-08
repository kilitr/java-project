package de.kilitr;

/**
 * An implementation of the data structure Graph (here a directed Graph). Can be both weighted and unweighted with this
 * implementation.
 * <p>
 * Unweighted means, that all Edges by default get <i>weight = 1</i> assigned.
 * </p>
 */
public class Graph extends BaseGraph {

    /**
     * @param verticeLabels An array, containing all ID's / labels of the vertices, that should be contained in
     *                      the Graph.
     */
    public Graph(String[] verticeLabels) {
        super();
        for (String verticeLabel : verticeLabels) {
            this.addVertex(new Vertex(verticeLabel));
        }
    }

    /**
     * Connects two vertices in one direction with custom weight.
     *
     * @param labelSource      label of vertex that this edge starts from.
     * @param labelDestination label of vertex that this edge leads to.
     * @param weight           The custom weight of this Edge.
     * @return true, if edge was successfully added, otherwise false.
     */
    public boolean addEdge(String labelSource, String labelDestination, int weight) {
        Vertex source = getVertex(labelSource);
        Vertex destination = getVertex(labelDestination);
        if (source == null || destination == null) return false;
        source.addEdge(new Edge(destination, weight));
        return true;
    }

    /**
     * Connects two vertices in one direction with unweighted Edge.
     * <p>
     *     Note: Sets weight of edge to 1. So will only work for unweighted graph if no Edge has been assigned a
     *     different weight!
     * </p>
     * @param labelSource label of vertex that this edge starts from.
     * @param labelDestination label of vertex that this edge leads to.
     * @return true, if edge was successfully added, otherwise false.
     */
    public boolean addEdge(String labelSource, String labelDestination) {
        return addEdge(labelSource, labelDestination, 1);
    }
}
