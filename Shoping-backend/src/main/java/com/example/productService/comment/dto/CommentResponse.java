package com.example.productService.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String body;
    private List<CommentResponse> commentResponses;
    private UserType userType;
    private Long userId;
    private String commentName;
    private LocalDateTime createdTime;
}
