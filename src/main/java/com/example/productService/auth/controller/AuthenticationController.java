package com.example.productService.auth.controller;

import com.example.productService.auth.service.AuthenticationService;
import com.example.productService.auth.dto.AuthenticationRequest;
import com.example.productService.auth.dto.AuthenticationResponse;
import com.example.productService.auth.dto.RegisterRequest;
import com.example.productService.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
            description = "Everything OK"),
            @ApiResponse(responseCode = "400 Bad Request",
                    description = "if there is error with input missing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),@ApiResponse(responseCode = "409 Conflict",
            description = "duplicate email",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @Operation(summary = "Register User, Manager, and Admin")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "400 Bad Request",
                    description = "if there is error with input missing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "Email is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),@ApiResponse(responseCode = "401 Unauthorized",
            description = "Password is invalid",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @Operation(summary = "Login User, Manager, and Admin")
    public AuthenticationResponse login(@RequestBody @Valid
                                                   AuthenticationRequest request) {
        return service.authenticate(request);
    }

}
