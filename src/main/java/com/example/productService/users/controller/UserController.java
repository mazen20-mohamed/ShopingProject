package com.example.productService.users.controller;
import com.example.productService.config.CurrentUser;
import com.example.productService.exception.ErrorResponse;
import com.example.productService.model.auth.User;
import com.example.productService.users.dto.UserInfoResponse;
import com.example.productService.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Operation")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "get user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public UserInfoResponse getUserInfo(@CurrentUser User user){
        return userService.getUserData(user);
    }
    
    @GetMapping("/{searchName}")
    @Operation(summary = "search for a user",description = "used in search bar and returns 10 users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public List<UserInfoResponse> getFirstTenUserSearch(@PathVariable String searchName){
        return userService.getFirstTenUserSearch(searchName);
    }

    @GetMapping("/getAllDisabledManagers")
    @Operation(summary = "find all disabled managers",description = "accessed only by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public List<UserInfoResponse> findAllManagersAreDisabled() {
        return userService.findAllManagersAreDisabled();
    }

    @GetMapping("enable/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "enable manager",description = "accessed only by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "manager id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void enableManager(@PathVariable Long managerId) {
        userService.enableManagerToCreateShop(managerId);
    }
    @Operation(summary = "check if manager is enabled",description = "accessed only by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/isEnabled")
    public boolean isEnabledManager(@CurrentUser User user){
        return userService.isEnabledManagerToCreateShop(user);
    }
    @Operation(summary = "check if token is expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/isTokenExpired")
    public boolean isTokenExpired(@RequestHeader("Authorization") String token){
        return userService.isTokenExpired(token);
    }
}
