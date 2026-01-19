package com.example.productService.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopSearchResponse {
    private Long id;
    private String name;
    private String shopImagePathURL;
}
