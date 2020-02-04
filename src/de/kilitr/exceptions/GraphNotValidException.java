package de.kilitr.exceptions;

/**
 * An exception, that gets thrown, when a Graph is invalid. (e.g. a not initialized Graph while running the GraphLoader)
 */
public class GraphNotValidException extends Exception {

    /**
     * An exception, that gets thrown, when a Graph is invalid. (e.g. a not initialized Graph while running the GraphLoader)
     *
     * @param errorMessage a custom errorMessage if required.
     */
    public GraphNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
