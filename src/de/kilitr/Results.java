package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

public class Results extends JavaProjectThread {
    private static final Logger logger = LogManager.getLogger(Results.class);

    private int amountVertices;
    private int amountEdges;
    private final ArrayList<String> vertexLabels;
    private final ArrayList<String> edgeLabels;
    private boolean isConnected;
    private int diameter; // TODO: implement graph diameter
    private final TreeMap<Vertex, TreeMap<Vertex, Path>> allPaths;
    private final TreeMap<Vertex, Double> allBetweenness;

    private final Graph graph;

    public Results(Graph g, Vertex vertexForBetweenness) {
        super(vertexForBetweenness);
        this.graph = g;
        this.vertexLabels = new ArrayList<>();
        this.edgeLabels = new ArrayList<>();
        this.allPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
        this.allBetweenness = new TreeMap<>(new TreeVertexAlphaNumComp());
    }

    public Results(Graph g, Vertex pathStart, Vertex pathDestination) {
        super(pathStart, pathDestination);
        this.graph = g;
        this.vertexLabels = new ArrayList<>();
        this.edgeLabels = new ArrayList<>();
        this.allPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
        this.allBetweenness = new TreeMap<>(new TreeVertexAlphaNumComp());
    }

    public Results(Graph g, boolean all) {
        super(all);
        this.graph = g;
        this.vertexLabels = new ArrayList<>();
        this.edgeLabels = new ArrayList<>();
        this.allPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
        this.allBetweenness = new TreeMap<>(new TreeVertexAlphaNumComp());
    }

    @Override
    public void run() {
        this.amountVertices = this.graph.getNumberOfVertices();
        this.amountEdges = this.graph.getNumberOfEdges();
        this.vertexLabels.addAll(this.graph.getVertexLabels());
        this.edgeLabels.addAll(this.graph.getEdgeLabels());
        this.isConnected = this.graph.isConnected();

        if (this.allFlag) {
            allShortestPaths(this.graph);
            allBetweennessCentrality(this.graph);
        } else if (this.singlePathFlag) {
            shortestPaths(this.graph, this.start, this.destination);
        } else if (this.singleBetweennessFlag) {
            betweennessCentrality(this.graph, this.start);
        }
    }

    public int getAmountVertices() {
        return this.amountVertices;
    }

    public int getAmountEdges() {
        return this.amountEdges;
    }

    public ArrayList<String> getVertexLabels() {
        return this.vertexLabels;
    }

    public ArrayList<String> getEdgeLabels() {
        return this.edgeLabels;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public int getDiameter() {
        return this.diameter;
    }

    public TreeMap<Vertex, TreeMap<Vertex, Path>> getAllPaths() {
        return this.allPaths;
    }

    public TreeMap<Vertex, Double> getAllBetweenness() {
        return this.allBetweenness;
    }


    private void allShortestPaths(Graph g) {
        for (Vertex start : g.getVertices()) {
            TreeMap<Vertex, Path> shortestPaths = new TreeMap<>(new TreeVertexAlphaNumComp());
            Dijkstra dijkstra = new Dijkstra(g, start);
            for (Vertex target : g.getVertices()) {
                Path path = dijkstra.createAllShortestPaths(target);
                shortestPaths.put(target, path);
                if (path.getWeight() > this.diameter) {
                    this.diameter = path.getWeight();
                    logger.debug("we're updating diameter");
                }
            }
            this.allPaths.put(start, shortestPaths);
        }
    }

    private void shortestPaths(Graph g, Vertex start, Vertex destination) {
        Dijkstra dijkstra = new Dijkstra(g, start);
        TreeMap<Vertex, Path> temp = new TreeMap<>(new TreeVertexAlphaNumComp());
        temp.put(destination, dijkstra.createAllShortestPaths(destination));
        this.allPaths.put(start, temp);
    }

    private void allBetweennessCentrality(Graph g) {
        Betweenness betweenness = new Betweenness(g);
        for (Vertex v : g.getVertices()) {
            this.allBetweenness.put(v, betweenness.getBetweenness(v));
        }
    }

    private void betweennessCentrality(Graph g, Vertex v) {
        Betweenness betweenness = new Betweenness(g);
        this.allBetweenness.put(v, betweenness.getBetweenness(v));
    }
}
