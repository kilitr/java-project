package de.kilitr;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 * Provides functionality to calculate all shortest paths.
 * Provides the weight of the shortest paths and all available shortest paths.
 */
public class Dijkstra {
    private static final Logger logger = LogManager.getLogger(Dijkstra.class);

    private final HashMap<Vertex, Integer> distance;
    private final HashMap<Vertex, List<Vertex>> predecessor;
    private final List<Vertex> q;


    /**
     * Initializes the shortest path calculation.
     *
     * @param g     The graph, that the starting vertex is contained in.
     * @param start The start vertex.
     */
    public Dijkstra(Graph g, Vertex start) {
        this.distance = new HashMap<>();
        this.predecessor = new HashMap<>();
        this.q = new ArrayList<>();
        for (Vertex v : g.getVertices()) {
            distance.put(v, Integer.MAX_VALUE);
            predecessor.put(v, null);
        }
        distance.put(start, 0);
        q.addAll(g.getVertices());
        execute();
    }

    /**
     * Initiates the calculation for the constructed Object.
     */
    private void execute() {
        while (!q.isEmpty()) {
            logger.debug("Queue: " + q.toString());
            q.sort(new VertexComparator());
            Vertex u = q.get(0);
            q.remove(0);
            logger.debug("Inspecting " + u.toString());
            for (Vertex v : u.getNeighbours()) {
                if (q.contains(v)) {
                    logger.debug("updating " + u.getLabel() + " & " + v.getLabel());
                    updateDistances(u, v);
                }
            }
        }
    }

    /**
     * creates all shortest paths from the starting vertex to the target in form of the class Paths.
     *
     * @param target The vertex, that you want to reach with all the shortest paths available.
     * @return custom class containing the weight of the shortest path and all the paths available.
     */
    public Path createAllShortestPaths(Vertex target) {
        Vertex u = target;

        int amountOfPaths = 0;
        while (predecessor.get(u) != null) {
            List<Vertex> predecessors = predecessor.get(u);
            amountOfPaths = amountOfPaths + (predecessors.size() - 1);
            u = predecessors.get(0);
        }
        amountOfPaths++;

        Path allPaths = new Path(distance.get(target));
        for(int i = 0; i < amountOfPaths; i++) {
            LinkedList<Vertex> path = new LinkedList<>();
            path.add(target);
            u = target;
            while (predecessor.get(u) != null) { // Predecessor of start is null
                List<Vertex> predecessors = predecessor.get(u);
                if(predecessors.size() > i) {
                    u = predecessors.get(i);
                } else {
                    u = predecessors.get(0);
                }
                path.add(0, u);
            }
            allPaths.addPath(path);
        }
        return allPaths;
    }

    private void updateDistances(Vertex origin, Vertex target) {
        logger.debug("Initial distance : " + distance.get(target));
        try {
            int alternative = distance.get(origin) + origin.getWeightTo(target);
            if (alternative < distance.get(target)) {
                distance.put(target, alternative);
                predecessor.put(target, List.of(origin));
                logger.debug("New distance for " + target.getLabel() + " = " + alternative);
            } else if (alternative == distance.get(target)) {
                // würde das dafür sorgen, dass 2 kürzeste Wege gefunden werden?
                List<Vertex> multiplePredecessors = new ArrayList<>();
                multiplePredecessors.add(predecessor.get(target).get(0));
                multiplePredecessors.add(origin);
                distance.put(target, alternative);
                logger.debug("New distance for " + target.getLabel() + " = " + alternative);
                predecessor.put(target, multiplePredecessors);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("Final distance : " + distance.get(target));
    }


    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return distance.get(v1).compareTo(distance.get(v2));
        }
    }
}
