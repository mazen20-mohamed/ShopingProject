package com.example.productService.notification.controller;
import com.example.productService.config.CurrentUser;
import com.example.productService.model.auth.User;
import com.example.productService.notification.dto.NotificationRequest;
import com.example.productService.notification.dto.NotificationResponse;
import com.example.productService.notification.service.NotificationGenerator;
import com.example.productService.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "Notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationGenerator notificationGenerator;
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @GetMapping("/{page}/{size}")
    public List<NotificationResponse> getAllNotification(@CurrentUser User user, @PathVariable int page , @PathVariable int size){
        return notificationService.getAllNotification(user,page,size);
    }

    @Scheduled(fixedDelay = 1000)
    public void generateUserNotification(){
        notificationGenerator.generateUserNotification();
    }

//    @PostMapping("/userMention/{userId}")
//    public void generateUserMention(@PathVariable Long userId,@RequestBody NotificationRequest notificationRequest)
//    {
//        notificationService.generateUserMention(userId,notificationRequest);
//    }
//
//    @PostMapping("/shopMention/{shopId}")
//    public void generateShopMention(@PathVariable Long shopId,@RequestBody NotificationRequest notificationRequest)
//    {
//        notificationService.generateShopMention(shopId,notificationRequest);
//    }
}
