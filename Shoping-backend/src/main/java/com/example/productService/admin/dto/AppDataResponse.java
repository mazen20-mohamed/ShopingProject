package com.example.productService.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppDataResponse {
    private long numberOfUsers;
    private long numberOfShops;
    private long numberOfPosts;
    private long numberOfComments;
    private long numberOfLikes;
}
