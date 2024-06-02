package com.example.productService.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String name;
    private String email;
    private int numberOfFollowingShops;
}
