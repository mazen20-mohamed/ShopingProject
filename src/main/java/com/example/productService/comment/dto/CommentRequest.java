package com.example.productService.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CommentRequest {
    @NotBlank
    private String body;
    @NotEmpty
    private Long postId;
    private Long commentParent;
}
