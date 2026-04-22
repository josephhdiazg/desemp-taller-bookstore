package co.edu.ustavillavo.desemp.taller_bookstore.mapper;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.RegisterRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.UserResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request, String encodedPassword) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}