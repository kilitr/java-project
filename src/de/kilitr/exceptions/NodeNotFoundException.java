package de.kilitr.exceptions;

/**
 * thrown, when a vertex can not be found in a graph.
 */
public class NodeNotFoundException extends Exception {

    /**
     * thrown, when a vertex can not be found in a graph.
     *
     * @param errorMessage if required, one can provide a custom error message.
     */
    public NodeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
