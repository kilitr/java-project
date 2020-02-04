package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

public class Results {
    private static final Logger logger = LogManager.getLogger(Results.class);

    private int amountVertices;
    private int amountEdges;
    private ArrayList<String> vertexLabels;
    private ArrayList<String> edgeLabels;
    private boolean isConnected;
    private int diameter; // TODO: implement graph diameter

    private TreeMap<Vertex, PathsFromVertex> allPaths;
    private TreeMap<Vertex, Double> allBetweenness;

    public Results(Graph g) {
        vertexLabels = new ArrayList<>();
        edgeLabels = new ArrayList<>();

        amountVertices = g.getNumberOfVertices();
        amountEdges = g.getNumberOfEdges();
        vertexLabels.addAll(g.getVertexLabels());
        edgeLabels.addAll(g.getEdgeLabels());
        isConnected = g.isConnected();

        allPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
        allBetweenness = new TreeMap<>(new TreeVertexAlphaNumComp());
    }

    public Results(Graph g, Vertex vertexForBetweenness) {
        this(g); // call basic constructor
        betweennessCentrality(g, vertexForBetweenness);
    }

    public Results(Graph g, Vertex pathStart, Vertex pathDestination) {
        this(g); // call basic constructor
        shortestPaths(g, pathStart, pathDestination);
    }

    public Results(Graph g, boolean all) {
        this(g);
        allShortestPaths(g);
        allBetweennessCentrality(g);
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

    public TreeMap<Vertex, PathsFromVertex> getAllPaths() {
        return allPaths;
    }

    public TreeMap<Vertex, Double> getAllBetweenness() {
        return allBetweenness;
    }


    private void allShortestPaths(Graph g) {
        for (Vertex start : g.getVertices()) {
            allPaths.put(start, new PathsFromVertex(g, start));
        }
    }

    private void shortestPaths(Graph g, Vertex start, Vertex destination) {
        allPaths.put(start, new PathsFromVertex(g, start));
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
