package co.edu.ustavillavo.desemp.taller_bookstore.exception.custom;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String bookTitle, int available, int requested) {
        super("Insufficient stock for '" + bookTitle + "': available=" + available + ", requested=" + requested);
    }
}