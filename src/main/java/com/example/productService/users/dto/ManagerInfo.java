package com.example.productService.users.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInfo {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private long shop_id;
}
