package de.kilitr;

class WeightedEdge extends Edge {
    private int weight;

    public WeightedEdge(Vertex to, int weight) {
        super(to);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
