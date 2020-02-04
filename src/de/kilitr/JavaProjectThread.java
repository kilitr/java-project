package de.kilitr;

/**
 * The superclass for all threads, whose behaviour is dependant on the commandline arguments.
 */
abstract class JavaProjectThread implements Runnable {

    protected boolean allFlag;
    protected boolean singlePathFlag;
    protected boolean singleBetweennessFlag;
    protected Node start;
    protected Node destination;

    /**
     * Basic constructor, called every time this object is constructed. Sets all flags to false.
     */
    public JavaProjectThread() {
        this.allFlag = false;
        this.singleBetweennessFlag = false;
        this.singlePathFlag = false;
    }

    /**
     * Constructor, that is being called when the commandline argument for the shortest path between two vertices is
     * invoked.
     *
     * @param pathStart       the start node
     * @param pathDestination the destination node
     */
    public JavaProjectThread(Node pathStart, Node pathDestination) {
        this();
        singlePathFlag = true;
        start = pathStart;
        destination = pathDestination;
    }

    /**
     * Constructor, that is being called when the commandline argument for the betweenness centrality measure of
     * a node is invoked.
     *
     * @param nodeForBetweenness calculate the betweennsess centrality for this node.
     */
    public JavaProjectThread(Node nodeForBetweenness) {
        this();
        singleBetweennessFlag = true;
        start = nodeForBetweenness;
    }

    /**
     * Constructor, that is being called when the commandline argument all calculations is invoked.
     *
     * @param all the parameter is only used for changing the signature of the Constructor. -> must be set to true though
     */
    public JavaProjectThread(boolean all) {
        this();
        allFlag = all;
    }
}
