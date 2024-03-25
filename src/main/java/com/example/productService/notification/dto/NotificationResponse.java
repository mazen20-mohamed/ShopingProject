package com.example.productService.notification.dto;
import com.example.productService.model.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String message;
    private List<Long> contentId;
    private NotificationType notificationType;
    private LocalDateTime createdTime;
}
