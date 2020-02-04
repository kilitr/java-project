package de.kilitr;

abstract class JavaProjectThread implements Runnable {

    protected boolean allFlag;
    protected boolean singlePathFlag;
    protected boolean singleBetweennessFlag;
    protected Vertex start;
    protected Vertex destination;

    public JavaProjectThread() {
        this.allFlag = false;
        this.singleBetweennessFlag = false;
        this.singlePathFlag = false;
    }

    public JavaProjectThread(Vertex pathStart, Vertex pathDestination) {
        this();
        singlePathFlag = true;
        start = pathStart;
        destination = pathDestination;
    }

    public JavaProjectThread(Vertex vertexForBetweenness) {
        this();
        singleBetweennessFlag = true;
        start = vertexForBetweenness;
    }

    public JavaProjectThread(boolean all) {
        this();
        allFlag = all;
    }
}
