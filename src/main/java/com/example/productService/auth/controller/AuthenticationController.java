package com.example.productService.auth.controller;

import com.example.productService.auth.service.AuthenticationService;
import com.example.productService.auth.dto.AuthenticationRequest;
import com.example.productService.auth.dto.AuthenticationResponse;
import com.example.productService.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return service.authenticate(request);
    }
    @GetMapping("/isTokenExpired")
    public boolean isTokenExpired(@RequestHeader("Authorization") String token){
        return service.isTokenExpired(token);
    }
}
