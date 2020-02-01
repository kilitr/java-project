package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Describes a single instance of the edges between a graph's vertices.
 */
public class Edge {
    private static final Logger logger = LogManager.getLogger(GraphLoader.class);

    private Vertex from; // for toString()
    private Vertex to;
    private int weight;

    /**
     * creates an Edge but <b>does not add</b> it to the respective vertices.
     * @param from   Vertex, that this Edge starts at.
     * @param to     Vertex, that this Edge points to.
     * @param weight The weight of this Edge.
     */
    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * provides the destination of the edge.
     * @return The vertex, that this edge points to.
     */
    protected Vertex getTo() {
        return to;
    }

    /**
     * provides the source of the edge.
     * @return The vertex, that this edge comes from.
     */
    protected Vertex getFrom() {
        return from;
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
        return "Edge{" + from.getLabel() + "-" + to.getLabel() + "(" + weight + ")}";
    }
}
