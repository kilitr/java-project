package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TreeMap;

public class PathsFromVertex {
    private static final Logger logger = LogManager.getLogger(PathsFromVertex.class);

    private TreeMap<Vertex, Path> allDestinationPaths;

    public PathsFromVertex(Graph g, Vertex from) {
        allDestinationPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
        createPaths(g, from);
    }

    private void createPaths(Graph g, Vertex from) {
        Dijkstra dijkstra = new Dijkstra(g, from);
        for (Vertex target : g.getVertices()) {
            allDestinationPaths.put(target, dijkstra.createAllShortestPaths(target));
        }
    }

    public Path getPath(Vertex to) {
        return allDestinationPaths.get(to);
    }

    public TreeMap<Vertex, Path> getAllDestinationPaths() {
        return allDestinationPaths;
    }

}
