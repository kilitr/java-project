package de.kilitr;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Paths {
    private ArrayList<LinkedList<Vertex>> paths;
    private int weight;

    public Paths(int w) {
        weight = w;
        paths = new ArrayList<>();
    }

    public void addPath(LinkedList<Vertex> path) {
        this.paths.add(path);
    };

    public void setWeight(int w) {
        this.weight = w;
    }

    @Override
    public String toString() {
        List<String> output = new ArrayList<>();
        for(LinkedList<Vertex> path : paths) {
            output.add(path.toString());
        }
        return String.join(" & ", output);
    }

    public int size() {
        return this.paths.size();
    }
}
