package com.example.productService.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class PostResponse {
    private String description;
    private List<byte[]> multipartFiles;
}
