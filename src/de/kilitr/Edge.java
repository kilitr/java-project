package de.kilitr;

class Edge {
    private Vertex to;
    private int weight;

    public Edge(Vertex to) {
        this.to = to;
        this.weight = 1;
    }

    public Edge(Vertex to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    public Vertex getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}
