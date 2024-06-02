package com.example.productService.auth.service;
import com.example.productService.config.JwtService;
import com.example.productService.auth.dto.AuthenticationRequest;
import com.example.productService.auth.dto.AuthenticationResponse;
import com.example.productService.auth.dto.RegisterRequest;
import com.example.productService.exception.BadRequestResponseException;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.repository.UserRepository;
import com.example.productService.users.dto.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
//        Optional<User> userFound = repository.findByEmail(request.getEmail());

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

        Optional<User> user = repository.findByEmail(request.getEmail());
        if(user.isEmpty()){
            throw new NotFoundResponseException("No Such a User with this email");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user.get());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();

    }

}
