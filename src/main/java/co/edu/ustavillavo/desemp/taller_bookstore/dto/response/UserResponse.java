package co.edu.ustavillavo.desemp.taller_bookstore.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}