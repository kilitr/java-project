package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

public class Results implements Runnable {
    private static final Logger logger = LogManager.getLogger(Results.class);

    private int amountVertices;
    private int amountEdges;
    private ArrayList<String> vertexLabels;
    private ArrayList<String> edgeLabels;
    private boolean isConnected;
    private int diameter; // TODO: implement graph diameter

    private TreeMap<Vertex, TreeMap<Vertex, Path>> allPaths;
    private TreeMap<Vertex, Double> allBetweenness;

    private boolean allFlag;
    private boolean singlePathFlag;
    private boolean singleBetweennessFlag;

    private Graph graph;
    private Vertex start;
    private Vertex destination;

    public Results(Graph g) {
        this.graph = g;

        allFlag = false;
        singlePathFlag = false;
        singleBetweennessFlag = false;

        vertexLabels = new ArrayList<>();
        edgeLabels = new ArrayList<>();
        allPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
        allBetweenness = new TreeMap<>(new TreeVertexAlphaNumComp());
    }

    public Results(Graph g, Vertex vertexForBetweenness) {
        this(g); // call basic constructor
        singleBetweennessFlag = true;
        start = vertexForBetweenness;
    }

    public Results(Graph g, Vertex pathStart, Vertex pathDestination) {
        this(g); // call basic constructor
        singlePathFlag = true;
        start = pathStart;
        destination = pathDestination;
    }

    public Results(Graph g, boolean all) {
        this(g);
        allFlag = all;
    }

    @Override
    public void run() {
        amountVertices = graph.getNumberOfVertices();
        amountEdges = graph.getNumberOfEdges();
        vertexLabels.addAll(graph.getVertexLabels());
        edgeLabels.addAll(graph.getEdgeLabels());
        isConnected = graph.isConnected();

        if (allFlag) {
            allShortestPaths(graph);
            allBetweennessCentrality(graph);
        } else if (singlePathFlag) {
            shortestPaths(graph, start, destination);
        } else if (singleBetweennessFlag) {
            betweennessCentrality(graph, start);
        }
    }

    public int getAmountVertices() {
        return amountVertices;
    }

    public int getAmountEdges() {
        return amountEdges;
    }

    public ArrayList<String> getVertexLabels() {
        return vertexLabels;
    }

    public ArrayList<String> getEdgeLabels() {
        return edgeLabels;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public int getDiameter() {
        return diameter;
    }

    public TreeMap<Vertex, TreeMap<Vertex, Path>> getAllPaths() {
        return allPaths;
    }

    public TreeMap<Vertex, Double> getAllBetweenness() {
        return allBetweenness;
    }


    private void allShortestPaths(Graph g) {
        for (Vertex start : g.getVertices()) {
            TreeMap<Vertex, Path> shortestPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
            Dijkstra dijkstra = new Dijkstra(g, start);
            for (Vertex target : g.getVertices()) {
                Path path = dijkstra.createAllShortestPaths(target);
                shortestPaths.put(target, path);
                if (path.getWeight() > diameter) {
                    diameter = path.getWeight();
                    logger.debug("we're updating diameter");
                }
            }
            allPaths.put(start, shortestPaths);
        }
    }

    private void shortestPaths(Graph g, Vertex start, Vertex destination) {
        TreeMap<Vertex, Path> shortestPath = new TreeMap<>(new TreeVertexAlphaNumComp());
        Dijkstra dijkstra = new Dijkstra(g, start);
        TreeMap<Vertex, Path> temp = new TreeMap<>(new TreeVertexAlphaNumComp());
        temp.put(destination, dijkstra.createAllShortestPaths(destination));
        allPaths.put(start, temp);
    }

    private void allBetweennessCentrality(Graph g) {
        Betweenness betweenness = new Betweenness(g);
        for (Vertex v : g.getVertices()) {
            allBetweenness.put(v, betweenness.getBetweenness(v));
        }
    }

    private void betweennessCentrality(Graph g, Vertex v) {
        Betweenness betweenness = new Betweenness(g);
        allBetweenness.put(v, betweenness.getBetweenness(v));
    }
}
