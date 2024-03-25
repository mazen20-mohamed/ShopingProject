package com.example.productService.shop.dto;

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
public class BranchRequest {
    @NotNull
    private int building_number;
    @NotBlank
    @NotNull
    private String street;
    @NotBlank
    @NotNull
    private String city;
    @NotBlank
    @NotNull
    private String government;
    @NotBlank
    @NotNull
    private String country;
    private String location;
    @NotEmpty(message = " there must be at least a phone number")
    @Valid
    private List<String> phones;
}
