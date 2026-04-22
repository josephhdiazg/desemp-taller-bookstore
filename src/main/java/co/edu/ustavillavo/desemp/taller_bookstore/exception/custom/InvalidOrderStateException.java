package co.edu.ustavillavo.desemp.taller_bookstore.exception.custom;

public class InvalidOrderStateException extends RuntimeException {
    public InvalidOrderStateException(String currentState, String attemptedAction) {
        super("Cannot perform '" + attemptedAction + "' on an order with status '" + currentState + "'");
    }
}