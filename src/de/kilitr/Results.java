package de.kilitr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Class that is capable of calculating and storing every information about this graph available in its own thread.
 * However, it does not always calculate everything.
 */
public class Results extends JavaProjectThread {
    private static final Logger logger = LogManager.getLogger(Results.class);

    private int amountVertices;
    private int amountEdges;
    private final ArrayList<String> nodeLabels;
    private final ArrayList<String> edgeLabels;
    private boolean isConnected;
    private int diameter; // TODO: implement graph diameter
    private final TreeMap<Node, TreeMap<Node, Path>> allPaths;
    private final TreeMap<Node, Double> allBetweenness;

    private final Graph graph;

    /**
     * Constructor for the betweenness centrality measure of a node command line argument.
     *
     * @param g                  contains the loaded graph that the calculations are meant for.
     * @param nodeForBetweenness calculate the betweenness centrality for this node.
     */
    public Results(Graph g, Node nodeForBetweenness) {
        super(nodeForBetweenness);
        this.graph = g;
        this.nodeLabels = new ArrayList<>();
        this.edgeLabels = new ArrayList<>();
        this.allPaths = new TreeMap<>(new TreeNodeAlphaNumComp());
        this.allBetweenness = new TreeMap<>(new TreeNodeAlphaNumComp());
    }

    /**
     * Constructor for the shortest path between two vertices command line argument.
     *
     * @param g               contains the loaded graph that the calculations are meant for.
     * @param pathStart       the start node of the path
     * @param pathDestination the destination node of the path
     */
    public Results(Graph g, Node pathStart, Node pathDestination) {
        super(pathStart, pathDestination);
        this.graph = g;
        this.nodeLabels = new ArrayList<>();
        this.edgeLabels = new ArrayList<>();
        this.allPaths = new TreeMap<>(new TreeNodeAlphaNumComp());
        this.allBetweenness = new TreeMap<>(new TreeNodeAlphaNumComp());
    }

    /**
     * Constructor for the command line argument for calculating everything.
     *
     * @param g   contains the loaded graph that the calculations are meant for.
     * @param all the parameter is only used for changing the signature of the Constructor. -> must be set to true though
     */
    public Results(Graph g, boolean all) {
        super(all);
        this.graph = g;
        this.nodeLabels = new ArrayList<>();
        this.edgeLabels = new ArrayList<>();
        this.allPaths = new TreeMap<>(new TreeNodeAlphaNumComp());
        this.allBetweenness = new TreeMap<>(new TreeNodeAlphaNumComp());
    }

    @Override
    public void run() {
        this.amountVertices = this.graph.getNumberOfVertices();
        this.amountEdges = this.graph.getNumberOfEdges();
        this.nodeLabels.addAll(this.graph.getNodeLabels());
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

    /**
     * Get the amount of vertices in the graph.
     *
     * @return amount of vertices
     */
    public int getAmountVertices() {
        return this.amountVertices;
    }

    /**
     * Get the amount of edges in the graph.
     *
     * @return amount of edges
     */
    public int getAmountEdges() {
        return this.amountEdges;
    }

    /**
     * Get the labels of all vertices in the graph.
     *
     * @return labels of all vertices in the graph.
     */
    public ArrayList<String> getNodeLabels() {
        return this.nodeLabels;
    }

    /**
     * Get the labels of all edges in the graph.
     *
     * @return get the labels of all edges in the graph.
     */
    public ArrayList<String> getEdgeLabels() {
        return this.edgeLabels;
    }

    /**
     * Whether or not the graph is connected.
     *
     * @return if the graph is connected or not.
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /**
     * Get the diameter of the graph. Note: This will only be calculated when invoking the program with the argument
     * for all calculations.
     *
     * @return diameter of the graph
     */
    public int getDiameter() {
        return this.diameter;
    }

    /**
     * Get all existing shortest paths in the graph.
     *
     * @return a nested Treemap: the outer treemap has the start node as key, the inner treemap has the
     * destination node as key and contains the paths as value.
     */
    public TreeMap<Node, TreeMap<Node, Path>> getAllPaths() {
        return this.allPaths;
    }

    /**
     * Get the betweenness centrality values for every node in the graph.
     *
     * @return a Treemap: the key is the node, the value is the betweenness centrality measure for the node.
     */
    public TreeMap<Node, Double> getAllBetweenness() {
        return this.allBetweenness;
    }


    private void allShortestPaths(Graph g) {
        for (Node start : g.getNodes()) {
            TreeMap<Node, Path> shortestPaths = new TreeMap<>(new TreeNodeAlphaNumComp());
            Dijkstra dijkstra = new Dijkstra(g, start);
            for (Node target : g.getNodes()) {
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

    private void shortestPaths(Graph g, Node start, Node destination) {
        Dijkstra dijkstra = new Dijkstra(g, start);
        TreeMap<Node, Path> temp = new TreeMap<>(new TreeNodeAlphaNumComp());
        temp.put(destination, dijkstra.createAllShortestPaths(destination));
        this.allPaths.put(start, temp);
    }

    private void allBetweennessCentrality(Graph g) {
        Betweenness betweenness = new Betweenness(g);
        for (Node node : g.getNodes()) {
            this.allBetweenness.put(node, betweenness.getBetweenness(node));
        }
    }

    private void betweennessCentrality(Graph g, Node node) {
        Betweenness betweenness = new Betweenness(g);
        this.allBetweenness.put(node, betweenness.getBetweenness(node));
    }
}
