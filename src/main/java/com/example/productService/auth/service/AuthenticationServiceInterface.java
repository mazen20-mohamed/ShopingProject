package com.example.productService.auth.service;

import com.example.productService.auth.dto.AuthenticationRequest;
import com.example.productService.auth.dto.AuthenticationResponse;
import com.example.productService.auth.dto.RegisterRequest;
import com.example.productService.model.auth.User;
import com.example.productService.users.dto.ChangePasswordRequest;

public interface AuthenticationServiceInterface {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
