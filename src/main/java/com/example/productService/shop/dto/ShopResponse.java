package com.example.productService.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private List<BranchResponse> branchRequests;
    private int numberOfFollowers;
    private double rate;
    private byte[] shopImage;
}
