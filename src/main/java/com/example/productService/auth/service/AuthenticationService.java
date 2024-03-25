package com.example.productService.auth.service;
import com.example.productService.config.JwtService;
import com.example.productService.auth.dto.AuthenticationRequest;
import com.example.productService.auth.dto.AuthenticationResponse;
import com.example.productService.auth.dto.RegisterRequest;
import com.example.productService.exception.BadRequestResponseException;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = repository.findByEmail(request.getEmail()).orElseThrow(null);
        if(user == null){
            throw new NotFoundResponseException("No Such a User with this email and password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public boolean isTokenExpired(String token){
        return jwtService.isTokenExpired(token);
    }
}
