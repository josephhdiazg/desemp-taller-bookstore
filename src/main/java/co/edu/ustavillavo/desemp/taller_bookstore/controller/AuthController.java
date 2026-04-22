package co.edu.ustavillavo.desemp.taller_bookstore.controller;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.LoginRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.RegisterRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.ApiResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.UserResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(authService.register(request), "User registered successfully", 201));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request), "Login successful"));
    }
}