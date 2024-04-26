package com.example.productService.util;

import com.example.productService.comment.dto.CommentResponse;
import com.example.productService.comment.dto.UserType;
import com.example.productService.model.auth.Role;
import com.example.productService.model.auth.User;
import com.example.productService.model.notification.Notification;
import com.example.productService.model.post.Comment;
import com.example.productService.model.post.Post;
import com.example.productService.model.shop.Branch;
import com.example.productService.model.shop.Phone;
import com.example.productService.model.shop.Shop;
import com.example.productService.notification.dto.NotificationResponse;
import com.example.productService.post.dto.PostResponse;
import com.example.productService.shop.dto.BranchResponse;
import com.example.productService.shop.dto.ShopResponse;
import com.example.productService.shop.dto.ShopSearchResponse;
import com.example.productService.users.dto.UserInfoResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ModelMapper {
    public static ShopResponse ConvertShopDTO(Shop shop) {

        return ShopResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .branchRequests(shop.getBranches().stream()
                        .map(Branch::getId)
                        .collect(Collectors.toList()))
                .numberOfFollowers(shop.getFollowers().size())
                .numberOfPosts(shop.getPosts().size())
                .shopImagePathURL(shop.getImagePathUrl())
                .rate(shop.getRate())
                .build();
    }
    public static BranchResponse ConvertBranchDTO(Branch branch){
        return BranchResponse.builder()
                .id(branch.getId())
                .city(branch.getCity())
                .location(branch.getLocation())
                .government(branch.getGovernment())
                .country(branch.getCountry())
                .building_number(branch.getBuilding_number())
                .street(branch.getStreet())
                .phones(branch.getPhoneList().stream()
                        .map(Phone::getPhone)
                        .collect(Collectors.toList()))
                .build();
    }
    public static PostResponse convertPostDTO(Post post, User user){
        // return response receipt to the needs
        return PostResponse.builder()
                .shopName(post.getShop().getName())
                .postsImagePathURL(post.getImagesAndVideosUrl())
                .shopImagePathURL(post.getShop().getImagePathUrl())
                .description(post.getDescription())
                .numberOfLikes(post.getLikedUsers().size())
                .isLikedFromUser(post.getLikedUsers().stream()
                        .anyMatch(like -> Objects.equals(like.getUser().getId(), user.getId())))
                .createdTime(post.getCreatedAt())
                .id(post.getId())
                .build();
    }

    public static UserInfoResponse convertUserDTO(User user){
        return UserInfoResponse.builder()
                .name(user.getFirstname() + " " + user.getLastname())
                .email(user.getEmail())
                .numberOfFollowingShops(user.getShopsFollowing().size())
                .id(user.getId())
                .build();
    }

    public static ShopSearchResponse convertShopSearchDTO(Shop shop){
        return ShopSearchResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .shopImagePathURL(shop.getImagePathUrl())
                .build();
    }

    public static CommentResponse convertCommentDTO(Comment comment){
        ArrayList<CommentResponse> comments = new ArrayList<>(comment.getComments().size());
        for(Comment i : comment.getComments()){
            comments.add(ModelMapper.convertCommentDTO(i));
        }

        Long userId;
        String commentName;
        UserType userType;
        if(comment.getUser().getRole() == Role.ROLE_USER)
        {
            User user = comment.getUser();
            userId = user.getId();
            commentName = user.getFirstname() + " " + user.getLastname();
            userType = UserType.USER_TYPE;
        }
        else
        {
            Shop shop = comment.getUser().getOwnShop();
            userId = shop.getId();
            commentName = shop.getName();
            userType = UserType.SHOP_TYPE;
        }

        return CommentResponse.builder()
                .body(comment.getBody())
                .id(comment.getId())
                .commentResponses(comments)
                .userId(userId)
                .commentName(commentName)
                .userType(userType)
                .createdTime(comment.getCreatedAt())
                .build();
    }

    public static NotificationResponse convertNotificationDTO(Notification notification){
        return NotificationResponse.builder()
                .notificationType(notification.getNotificationType())
                .contentId(notification.getContentId())
                .message(notification.getMessage())
                .id(notification.getId())
                .createdTime(notification.getCreatedAt())
                .build();
    }

}
