package com.example.productService.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponse {
    private Long id;
    private String name;
    private String category;
    private String description;
    private List<Long> branchRequests;
    private int numberOfFollowers;
    private int numberOfPosts;
    private double rate; // still not sure
    private String shopImagePathURL;
}
