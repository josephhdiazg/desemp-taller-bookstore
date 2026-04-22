package co.edu.ustavillavo.desemp.taller_bookstore.service.impl;

import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.LoginRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.request.RegisterRequest;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.AuthResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.dto.response.UserResponse;
import co.edu.ustavillavo.desemp.taller_bookstore.entity.User;
import co.edu.ustavillavo.desemp.taller_bookstore.exception.custom.DuplicateResourceException;
import co.edu.ustavillavo.desemp.taller_bookstore.mapper.UserMapper;
import co.edu.ustavillavo.desemp.taller_bookstore.repository.UserRepository;
import co.edu.ustavillavo.desemp.taller_bookstore.security.JwtService;
import co.edu.ustavillavo.desemp.taller_bookstore.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }
        User user = userMapper.toEntity(request, passwordEncoder.encode(request.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .expiresIn(jwtService.getExpiration())
                .role(user.getRole().name())
                .build();
    }
}