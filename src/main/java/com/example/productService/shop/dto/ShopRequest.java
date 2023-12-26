package com.example.productService.shop.dto;

import com.example.productService.shop.dto.BranchRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequest {
    private String name;
    private String category;
    private String description;
    private List<BranchRequest> branchRequests;



}
