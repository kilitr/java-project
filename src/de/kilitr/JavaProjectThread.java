package de.kilitr;

abstract class JavaProjectThread implements Runnable {

    protected boolean allFlag;
    protected boolean singlePathFlag;
    protected boolean singleBetweennessFlag;
    protected Vertex start;
    protected Vertex destination;

    public JavaProjectThread(Object o) {
        this.allFlag = false;
        this.singleBetweennessFlag = false;
        this.singlePathFlag = false;
    }

    public JavaProjectThread(Object o, Vertex pathStart, Vertex pathDestination) {
        this(o);
        singlePathFlag = true;
        start = pathStart;
        destination = pathDestination;
    }

    public JavaProjectThread(Object o, Vertex vertexForBetweenness) {
        this(o);
        singleBetweennessFlag = true;
        start = vertexForBetweenness;
    }

    public JavaProjectThread(Object o, boolean all) {
        this(o);
        allFlag = all;
    }
}
