package com.example.productService.notification.service;

import com.example.productService.model.auth.User;
import com.example.productService.model.notification.NotificationType;
import com.example.productService.model.notification.UserNotification;
import com.example.productService.model.post.Post;
import com.example.productService.model.shop.Shop;
import com.example.productService.post.service.PostServiceImpl;
import com.example.productService.repository.UserRepository;
import com.example.productService.repository.notification.UserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationGenerator {
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;
    private static final Logger logger = LoggerFactory.getLogger(NotificationGenerator.class);

    // generate every 6 hours
    @Transactional
    public void generateUserNotification(){
//        Optional<List<User>> users = userRepository.findAllUsers();
//        logger.info("has working prop");
//        if(users.isEmpty())
//            return;
//
//        users.get().forEach(
//                user -> {
//                    // each user
//                    logger.info("enter the user area");
//                    List<Long> ids = new ArrayList<>();
//                    List<String> shopName = new ArrayList<>();
//                    List<Shop> shops = user.getShopsFollowing();
//                    if(shops.isEmpty())
//                        return;
//
//                    shops.forEach(
//                            // each following shop
//                            shop -> {
//                                logger.info("enter the shops area");
//                                // get all new posts from that shop
//                                List<Post> posts = shop.getPosts();
//                                if(posts.isEmpty())
//                                    return;
//                                logger.info("enter the posts area");
//                                List<Long> postsId = posts.stream().filter(post ->
//                                    post.getCreatedAt().isAfter(LocalDateTime
//                                            .now()
//                                            .minusNanos(21600000L))
//                                ).map(Post::getId).toList();
//                                ids.addAll(postsId);
//                                shopName.add(shop.getName());
//                            }
//                    );
//
//                    StringBuilder message;
//                    if(shopName.isEmpty())
//                        return;
//                    if(shopName.size()>2){
//                        message = new StringBuilder("New Posts added from ").append(shopName.getFirst())
//                                .append(", ").append(shopName.get(1))
//                                .append(" and others.");
//                    }
//                    else{
//                        message = new StringBuilder("New Posts added from ").append(shopName.getFirst())
//                                .append(", and others.");
//                    }
//
//                    UserNotification userNotification =
//                            UserNotification.builder()
//                                    .user(user)
//                                    .contentId(ids)
//                                    .notificationType(NotificationType.POST)
//                                    .message(message.toString())
//                                    .build();
//
//                    userNotificationRepository.save(userNotification);
//                }
//        );
    }
}
