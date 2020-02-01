package de.kilitr.exceptions;

public class VertexNotFoundException extends Exception {
    public VertexNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public VertexNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
