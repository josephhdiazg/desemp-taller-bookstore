package co.edu.ustavillavo.desemp.taller_bookstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthorRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String biography;

    @Email(message = "Email must be valid")
    private String email;
}