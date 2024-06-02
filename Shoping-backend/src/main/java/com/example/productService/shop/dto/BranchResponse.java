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
public class BranchResponse {
    private Long id;
    private int building_number;
    private String street;
    private String city;
    private String government;
    private String country;
    private String location;
    private List<String> phones;
}
