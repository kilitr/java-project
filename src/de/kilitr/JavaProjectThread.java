package de.kilitr;

/**
 * The superclass for all threads, whose behaviour is dependant on the commandline arguments.
 */
abstract class JavaProjectThread implements Runnable {

    protected boolean allFlag;
    protected boolean singlePathFlag;
    protected boolean singleBetweennessFlag;
    protected Vertex start;
    protected Vertex destination;

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
     * @param pathStart       the start Vertex
     * @param pathDestination the destination Vertex
     */
    public JavaProjectThread(Vertex pathStart, Vertex pathDestination) {
        this();
        singlePathFlag = true;
        start = pathStart;
        destination = pathDestination;
    }

    /**
     * Constructor, that is being called when the commandline argument for the betweenness centrality measure of
     * a vertex is invoked.
     *
     * @param vertexForBetweenness calculate the betweennsess centrality for this vertex.
     */
    public JavaProjectThread(Vertex vertexForBetweenness) {
        this();
        singleBetweennessFlag = true;
        start = vertexForBetweenness;
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
