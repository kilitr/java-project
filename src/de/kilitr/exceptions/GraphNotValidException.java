package de.kilitr.exceptions;

public class GraphNotValidException extends Exception {
    public GraphNotValidException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public GraphNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
