package com.example.productService.notification.dto;

import com.example.productService.model.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String message;
    private List<Long> contentId;
    private NotificationType notificationType;
}
