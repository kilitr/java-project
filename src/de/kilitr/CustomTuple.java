package de.kilitr;

public class CustomTuple {
    private int weight;
    private Vertex parent;

    public CustomTuple(int w, Vertex p) {
        setWeight(w);
        setParent(p);
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
