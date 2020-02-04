package de.kilitr;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides functionality to store multiple shortest Paths with the according weight.
 */
public class Path {

    private final ArrayList<LinkedList<Node>> paths;
    private final int weight;

    /**
     * Initializes the contained data structures and sets the weight to <i>w</i>.
     *
     * @param w weight for all paths contained in this object
     */
    public Path(int w) {
        weight = w;
        paths = new ArrayList<>();
    }

    /**
     * Adds a new path.
     *
     * @param path the new path to add
     */
    public void addPath(LinkedList<Node> path) {
        this.paths.add(path);
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
     * @param node a node of the Graph
     * @return The number of paths which contain the node v
     */
    public int checkNode(Node node) {
        int pathsWithNode = 0;
        for (LinkedList<Node> l : paths) {
            if (l.getFirst() == node || l.getLast() == node) {
                break;
            } else if (l.contains(node)) {
                pathsWithNode++;
            }
        }
        return pathsWithNode;
    }

    /**
     * generates a String describing this Object.
     *
     * @return a String in the form of <i>[node1, node2, node3]</i> or <i>[node1, node2, node3] &amp; [node2, node3, node1]</i>, if multiple paths are present.
     */
    @Override
    public String toString() {
        List<String> output = new ArrayList<>();
        for (LinkedList<Node> path : paths) {
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