package de.kilitr;

/**
 * A class describing the edges of a graph.
 */
public class Edge implements Comparable<Edge>{
    private Vertex from; // for toString()
    private Vertex to;
    private int weight;

    /**
     * @param to Vertex, that this Edge points to.
     */
    public Edge(Vertex from, Vertex to) {
        this.to = to;
        this.weight = 1;
    }

    /**
     * @param to     Vertex, that this Edge points to.
     * @param weight The weight of this Edge.
     */
    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * @return The vertex, that this edge points to.
     */
    protected Vertex getTo() {
        return to;
    }

    /**
     * @return The vertex, that this edge comes from.
     */
    protected Vertex getFrom() {
        return from;
    }

    /**
     * @return The weight of this Edge.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * TODO: Add JavaDoc
     * @param edge
     * @return
     */
    @Override
    public int compareTo(Edge edge) {
        if(this.weight < edge.weight)
            return -1;
        else if(this.weight > edge.weight)
            return 1;
        return 0; // equal
    }

    @Override
    public String toString() {
        return "[" + from.getLabel() + "]==(" + weight + ")==>[" + to.getLabel() + "]";
    }
}
