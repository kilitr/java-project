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
    /**
     * @param v a Vertex of the Graph
     * @return The number of paths which contain the vertex v
     */
    public int checkVertex(Vertex v){
        int pathsWithVertex = 0;
        for (LinkedList l : paths){
            if (l.getFirst() == v || l.getLast() == v){
                break;
            }
            else if(l.contains(v)){
                pathsWithVertex++;
            }
        }
        return pathsWithVertex;
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
