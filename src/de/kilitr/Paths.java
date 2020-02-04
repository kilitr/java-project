package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides functionality to store multiple shortest Paths with the according weight.
 */
public class Paths {
    private static final Logger logger = LogManager.getLogger(Paths.class);

    private ArrayList<LinkedList<Vertex>> paths;
    private int weight;

    /**
     * Initializes the contained data structures and sets the weight to <i>w</i>.
     *
     * @param w weight for all paths contained in this object
     */
    public Paths(int w) {
        weight = w;
        paths = new ArrayList<>();
    }

    /**
     * Adds a new path.
     *
     * @param path the new path to add
     */
    public void addPath(LinkedList<Vertex> path) {
        this.paths.add(path);
    }

    /**
     * change the weight of all contained paths.
     *
     * @param w changes the weight.
     */
    public void setWeight(int w) {
        this.weight = w;
    }

    /**
     * provides the weight of these shortest paths.
     *
     * @return weight of shortest paths.
     */
    public int getWeight() {
        return this.weight;
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

    /**
     * generates a String describing this Object.
     * @return a String in the form of <i>[vertex1, vertex2, vertex3]</i> or <i>[vertex1, vertex2, vertex3] &amp; [vertex2, vertex3, vertex1]</i>, if multiple paths are present.
     */
    @Override
    public String toString() {
        List<String> output = new ArrayList<>();
        for(LinkedList<Vertex> path : paths) {
            output.add(path.toString());
        }
        return String.join(" & ", output);
    }


    /**
     * provides the amount of existing paths.
     * @return amount of paths contained.
     */
    public int size() {
        return this.paths.size();
    }
}
