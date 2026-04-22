package co.edu.ustavillavo.desemp.taller_bookstore.service;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.LoginRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.RegisterRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}