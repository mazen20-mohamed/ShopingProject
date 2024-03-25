package com.example.productService.notification.service;

import com.example.productService.model.auth.Role;
import com.example.productService.model.auth.User;
import com.example.productService.model.notification.ManagerNotification;
import com.example.productService.model.notification.NotificationType;
import com.example.productService.model.notification.UserNotification;
import com.example.productService.model.shop.Shop;
import com.example.productService.notification.dto.NotificationRequest;
import com.example.productService.notification.dto.NotificationResponse;
import com.example.productService.repository.UserRepository;
import com.example.productService.repository.notification.ManagerNotificationRepository;
import com.example.productService.repository.notification.UserNotificationRepository;
import com.example.productService.shop.service.ShopService;
import com.example.productService.shop.service.ShopServiceImpl;
import com.example.productService.users.service.UserService;
import com.example.productService.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final ManagerNotificationRepository managerNotificationRepository;
    // get all notification for both user and manager
    public List<NotificationResponse> getAllNotification(User user , int page , int size ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if(user.getRole() == Role.ROLE_USER){
            Page<UserNotification> notifications =  userNotificationRepository.findByUserId(user.getId(),pageable);
            return notifications.getContent().stream().map(
                    ModelMapper::convertNotificationDTO
            ).toList();
        }
        Page<ManagerNotification> managerNotifications =  managerNotificationRepository.findByManagerId(user.getId(),pageable);
        return managerNotifications.getContent().stream().map(
                ModelMapper::convertNotificationDTO
        ).toList();
    }

    public void generateUserNotification(UserNotification userNotification){
        userNotificationRepository.save(userNotification);
    }
    public void generateManagerNotification(ManagerNotification managerNotification){
        managerNotificationRepository.save(managerNotification);
    }

    /*
        the user has created a mention for userId
    */
//    public void generateUserMention(Long userId, NotificationRequest notificationRequest){
//        User user =  userService.getUserById(userId);
//        UserNotification userNotification =
//                UserNotification.builder()
//                        .user(user)
//                        .contentId(notificationRequest.getContentId())
//                        .notificationType(notificationRequest.getNotificationType())
//                        .message(notificationRequest.getMessage())
//                        .build();
//        userNotificationRepository.save(userNotification);
//    }
//
//    public void generateShopMention(Long shopId, NotificationRequest notificationRequest){
//        Shop shop =  shopService.getShopByIdOptional(shopId);
//        ManagerNotification managerNotification =
//                ManagerNotification.builder()
//                        .manager(shop.getManager())
//                        .contentId(notificationRequest.getContentId())
//                        .notificationType(notificationRequest.getNotificationType())
//                        .message(notificationRequest.getMessage())
//                        .build();
//        managerNotificationRepository.save(managerNotification);
//    }

}
