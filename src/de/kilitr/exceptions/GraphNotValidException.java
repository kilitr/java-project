package de.kilitr.exceptions;

/**
 * thrown, when a Graph is invalid. (e.g. a not initialized Graph while running the GraphLoader)
 */
public class GraphNotValidException extends Exception {

    /**
     * thrown, when a Graph is invalid. (e.g. a not initialized Graph while running the GraphLoader)
     *
     * @param errorMessage a custom errorMessage if required.
     */
    public GraphNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
