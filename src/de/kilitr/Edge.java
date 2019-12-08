package de.kilitr;

/**
 * A class describing the edges of a graph.
 */
public class Edge {
    private Vertex to;
    private int weight;

    /**
     * @param to Vertex, that this Edge points to.
     */
    public Edge(Vertex to) {
        this.to = to;
        this.weight = 1;
    }

    /**
     * @param to     Vertex, that this Edge points to.
     * @param weight The weight of this Edge.
     */
    public Edge(Vertex to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    /**
     * @return The vertex, that this edge points to.
     */
    public Vertex getTo() {
        return to;
    }

    /**
     * @return The weight of this Edge.
     */
    public int getWeight() {
        return weight;
    }
}
