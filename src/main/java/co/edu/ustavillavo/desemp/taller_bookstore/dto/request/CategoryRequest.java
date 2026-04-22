package co.edu.ustavillavo.desemp.taller_bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description;
}