package co.edu.ustavillavo.desemp.taller_bookstore.dto.response;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {

    private String status;
    private int code;
    private String message;
    private List<String> errors;
    private Instant timestamp;
    private String path;

    public ApiErrorResponse(int code, String message, List<String> errors, String path) {
        this.status = "error";
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now();
        this.path = path;
    }

    public String getStatus() { return status; }
    public int getCode() { return code; }
    public String getMessage() { return message; }
    public List<String> getErrors() { return errors; }
    public Instant getTimestamp() { return timestamp; }
    public String getPath() { return path; }
}