package de.kilitr;

class Edge {
    private Vertex to;

    protected Edge(Vertex to) {
        this.to = to;
    }

    public Vertex getTo() {
        return to;
    }
}
