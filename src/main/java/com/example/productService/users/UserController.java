package com.example.productService.users;

import com.example.productService.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "follow/shop/{id}")
    @PreAuthorize("hasRole('USER')")
    public void followShop(@PathVariable Long id,@RequestHeader(name = "Authorization") String token){
        userService.followShop(id,token);
    }

}
