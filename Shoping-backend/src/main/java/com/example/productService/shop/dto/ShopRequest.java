package com.example.productService.shop.dto;

import com.example.productService.shop.dto.BranchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = " must not be null")
    @NotBlank(message = " must not be blank")
    private String name;
    private String category;
    @NotBlank
    private String description;
    @NotEmpty(message = " there must at a least one branch")
    @Valid
    private List<BranchRequest> branchRequests;
}
