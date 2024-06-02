package com.example.productService.notification.controller;
import com.example.productService.config.CurrentUser;
import com.example.productService.model.auth.User;
import com.example.productService.notification.dto.NotificationResponse;
import com.example.productService.notification.service.NotificationService;
import com.example.productService.post.dto.PagedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "Notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{page}/{size}")
    public PagedResponse<NotificationResponse> getAllNotification(@CurrentUser User user, @PathVariable int page , @PathVariable int size){
        return notificationService.getAllNotification(user,page,size);
    }

}
