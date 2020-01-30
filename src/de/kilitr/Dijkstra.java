package de.kilitr;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Dijkstra {
    private static final Logger logger = LogManager.getLogger(Dijkstra.class);

    private Graph graph;
    private Vertex start;
    private HashMap<Vertex, Integer> distance;
    private HashMap<Vertex, List<Vertex>> predecessor;
    private List<Vertex> q;


    public Dijkstra(Graph g, Vertex start) {
        this.distance = new HashMap<>();
        this.predecessor = new HashMap<>();
        this.q = new ArrayList<>();
        this.graph = g;
        this.start = start;
        for (Vertex v : this.graph.getVertices()) {
            distance.put(v, Integer.MAX_VALUE);
            predecessor.put(v, null);
        }
        distance.put(this.start, 0);
        q.addAll(this.graph.getVertices());
    }

    public HashMap<Vertex, List<Vertex>> execute() {
        while (!q.isEmpty()) {
            logger.debug("Queue: " + q.toString());
            q.sort(new VertexComparator());
            Vertex u = q.get(0);
            q.remove(0);
            logger.debug("Inspecting " + u.toString());
            for (Vertex v : u.getNeighbours()) {
                if (q.contains(v)) {
                    logger.debug("updating " + u.getLabel() + " & " + v.getLabel());
                    update(u, v);
                }
            }
        }
        return predecessor;
    }

    public LinkedList<Vertex> createShortestPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        path.add(target);
        Vertex u = target;
        while (predecessor.get(u) != null) { // Predecessor of start is null
            u = predecessor.get(u).get(0);
            path.add(0, u);
        }
        return path;
    }

    public Paths createAllShortestPaths(Vertex target) {
        Vertex u = target;

        int amountOfPaths = 0;
        while (predecessor.get(u) != null) {
            List<Vertex> predecessors = predecessor.get(u);
            amountOfPaths = amountOfPaths + (predecessors.size() - 1);
            u = predecessors.get(0);
        }
        amountOfPaths++;

        Paths allPaths = new Paths(distance.get(target));
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

    private void update(Vertex origin, Vertex target) {
        logger.debug("Initial distance : " + distance.get(target));
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
        logger.debug("Final distance : " + distance.get(target));
    }

    public HashMap<Vertex, Integer> getDistances() {
        return distance;
    }

    public Integer getDistanceTo(Vertex v) {
        return distance.get(v);
    }

    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return distance.get(v1).compareTo(distance.get(v2));
        }
    }
}
