package de.kilitr.exceptions;

/**
 * Custom Exception, that is being thrown, when attempting to add duplicate vertices to a graph.
 */
public class DuplicateNodeException extends Exception {

    /**
     * Custom Exception, that is being thrown, when attempting to add duplicate vertices to a graph.
     *
     * @param errorMessage if required, one can provide a custom error message.
     */
    public DuplicateNodeException(String errorMessage) {
        super(errorMessage);
    }
}
