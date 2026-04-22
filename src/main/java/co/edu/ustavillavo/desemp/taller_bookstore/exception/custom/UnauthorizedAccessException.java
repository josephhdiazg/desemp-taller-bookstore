package co.edu.ustavillavo.desemp.taller_bookstore.exception.custom;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("You are not authorized to access this resource");
    }
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}