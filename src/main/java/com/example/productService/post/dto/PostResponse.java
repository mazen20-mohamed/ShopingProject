package com.example.productService.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String shopImagePathURL;
    private String shopName;
    private List<String> postsImagePathURL;
    private String description;
    private long numberOfLikes;
    private boolean isLikedFromUser;
    private LocalDateTime createdTime;
}
