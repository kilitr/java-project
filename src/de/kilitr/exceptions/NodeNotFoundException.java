package de.kilitr.exceptions;

/**
 * Custom Exception, that is being thrown, when a vertex can not be found in a graph.
 */
public class NodeNotFoundException extends Exception {

    /**
     * Custom Exception, that is being thrown, when a vertex can not be found in a graph.
     *
     * @param errorMessage if required, one can provide a custom error message.
     */
    public NodeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
