package com.example.productService.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class PostRequest {
    private String description;
    private List<MultipartFile> multipartFiles;

}
