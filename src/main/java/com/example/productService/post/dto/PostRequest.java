package com.example.productService.post.dto;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = " must not be null")
    private List<MultipartFile> imgsAndVideosOfPost;
}
