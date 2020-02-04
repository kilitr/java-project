package de.kilitr;

/**
 * Describes a single instance of the edges between a graph's vertices.
 */
public class Edge {

    private final Node from; // for toString()
    private final Node to;
    private final int weight;

    /**
     * creates an Edge but <b>does not add</b> it to the respective vertices.
     *
     * @param from   node, that this Edge starts at.
     * @param to     node, that this Edge points to.
     * @param weight The weight of this Edge.
     */
    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * provides the destination of the edge.
     *
     * @return The node, that this edge points to.
     */
    protected Node getTo() {
        return to;
    }

    /**
     * provides the weight of this edge.
     * @return The weight of this Edge.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * generates a String describing this Object.
     *
     * @return a String in the form of <i>Edge{[source]-[target]([weight]}</i>.
     */
    @Override
    public String toString() {
        return "\"" + from.getLabel() + "-" + to.getLabel() + "\"";
    }

    /**
     * reverses an edge by swapping the from and the to node.
     *
     * @return the reversed edge.
     */
    public Edge reversed() {
        return new Edge(this.to, this.from, this.weight);
    }
}
