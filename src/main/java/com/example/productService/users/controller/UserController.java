package com.example.productService.users.controller;

import com.example.productService.config.CurrentUser;
import com.example.productService.model.auth.User;
import com.example.productService.users.dto.UserInfoResponse;
import com.example.productService.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserInfoResponse getUserInfo(@CurrentUser User user){
        return userService.getUserData(user);
    }
    
    @GetMapping("/{searchName}")
    public List<UserInfoResponse> getFirstTenUserSearch(@PathVariable String searchName){
        return userService.getFirstTenUserSearch(searchName);
    }

    @GetMapping("/getAllDisabledManagers")
    public List<UserInfoResponse> findAllManagersAreDisabled() {
        return userService.findAllManagersAreDisabled();
    }

    @GetMapping("enable/{managerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void enableManager(@PathVariable Long managerId) {
        userService.enableManagerToCreateShop(managerId);
    }
    @GetMapping("isEnabled")
    public boolean isEnabledManager(@CurrentUser User user){
        return userService.isEnabledManagerToCreateShop(user);
    }
}
