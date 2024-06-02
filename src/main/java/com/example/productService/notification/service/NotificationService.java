package com.example.productService.notification.service;

import com.example.productService.comment.dto.CommentRequest;
import com.example.productService.model.auth.Role;
import com.example.productService.model.auth.User;
import com.example.productService.model.notification.ManagerNotification;
import com.example.productService.model.notification.NotificationType;
import com.example.productService.model.notification.UserNotification;
import com.example.productService.model.post.Comment;
import com.example.productService.notification.dto.NotificationRequest;
import com.example.productService.notification.dto.NotificationResponse;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.repository.notification.ManagerNotificationRepository;
import com.example.productService.repository.notification.UserNotificationRepository;
import com.example.productService.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final ManagerNotificationRepository managerNotificationRepository;

    // get all notification for both user or manager
    public PagedResponse<NotificationResponse> getAllNotification(User user , int page , int size ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if(user.getRole() == Role.ROLE_USER){
            Page<UserNotification> notifications =  userNotificationRepository.findByUserId(user.getId(),pageable);
            List<NotificationResponse> notificationList = notifications.getContent().stream().map(
                    ModelMapper::convertNotificationDTO
            ).toList();
            return new PagedResponse<>(notificationList, notifications.getNumber(),
                    notifications.getSize(), notificationList.size(),
                    notifications.getTotalPages(), notifications.isLast());
        }
        Page<ManagerNotification> managerNotifications =  managerNotificationRepository.findByManagerId(user.getId(),pageable);
        List<NotificationResponse> notificationList = managerNotifications.getContent().stream().map(
                ModelMapper::convertNotificationDTO
        ).toList();
        return new PagedResponse<>(notificationList, managerNotifications.getNumber(),
                managerNotifications.getSize(), notificationList.size(),
                managerNotifications.getTotalPages(), managerNotifications.isLast());
    }

    public void addUserNotification(UserNotification userNotification){
        userNotificationRepository.save(userNotification);
    }

    public void addManagerNotification(ManagerNotification managerNotification){
        managerNotificationRepository.save(managerNotification);
    }

    public void generateCommentNotification(User user , Comment parentComment, CommentRequest comment){
        User parentUser = parentComment.getUser();
        if(parentUser.getRole() == Role.ROLE_USER){
            UserNotification userNotification =
                    UserNotification.builder()
                            .user(parentUser)
                            .notificationType(NotificationType.COMMENT)
                            .contentId(Arrays.asList(comment.getPostId(),parentComment.getId()))
                            .message("Reply on your comment from "+user.getFirstname())
                            .build();
            userNotificationRepository.save(userNotification);
        }
        else{
            ManagerNotification managerNotification =
                    ManagerNotification.builder()
                            .manager(parentUser)
                            .notificationType(NotificationType.COMMENT)
                            .contentId(Arrays.asList(comment.getPostId(),parentComment.getId()))
                            .message("Reply on your comment from "+user.getFirstname())
                            .build();
            managerNotificationRepository.save(managerNotification);
        }
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
