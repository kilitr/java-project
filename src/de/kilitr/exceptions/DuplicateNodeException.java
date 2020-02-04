package de.kilitr.exceptions;

/**
 * thrown, when attempting to add duplicate vertices to a graph.
 */
public class DuplicateNodeException extends Exception {

    /**
     * thrown, when attempting to add duplicate vertices to a graph.
     *
     * @param errorMessage if required, one can provide a custom error message.
     */
    public DuplicateNodeException(String errorMessage) {
        super(errorMessage);
    }
}
