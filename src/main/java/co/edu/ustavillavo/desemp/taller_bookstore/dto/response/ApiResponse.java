package co.edu.ustavillavo.desemp.taller_bookstore.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String status;
    private int code;
    private String message;
    private T data;
    private Instant timestamp;

    private ApiResponse() {}

    public static <T> ApiResponse<T> success(T data, String message, int code) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = "success";
        response.code = code;
        response.message = message;
        response.data = data;
        response.timestamp = Instant.now();
        return response;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return success(data, message, 200);
    }

    public String getStatus() { return status; }
    public int getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Instant getTimestamp() { return timestamp; }
}