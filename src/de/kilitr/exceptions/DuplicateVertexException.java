package de.kilitr.exceptions;

public class DuplicateVertexException extends Exception {
    public DuplicateVertexException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public DuplicateVertexException(String errorMessage) {
        super(errorMessage);
    }
}
