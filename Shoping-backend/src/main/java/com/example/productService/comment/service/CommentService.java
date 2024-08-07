package com.example.productService.comment.service;

import com.example.productService.comment.dto.CommentRequest;
import com.example.productService.comment.dto.CommentResponse;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.model.notification.Notification;
import com.example.productService.model.notification.NotificationType;
import com.example.productService.model.notification.UserNotification;
import com.example.productService.model.post.Comment;
import com.example.productService.model.post.Post;
import com.example.productService.notification.service.NotificationService;
import com.example.productService.post.service.PostServiceImpl;
import com.example.productService.repository.post.CommentRepository;
import com.example.productService.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostServiceImpl postService;
    private final NotificationService notificationService;
    private Comment getCommentByIdWithCheck(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()){
            log.error("There is no comment with id {}",commentId);
            throw new NotFoundResponseException("There is no comment with id "+commentId);
        }
        return comment.get();
    }

    public void createComment(CommentRequest commentRequest, User user){
        Comment parentComment = null;
        if(commentRequest.getCommentParent() !=null){
            Optional<Comment> parentCommentOptional = commentRepository.findById(commentRequest.getCommentParent());
            if(parentCommentOptional.isEmpty()){
                throw new NotFoundResponseException("There is no parent comment with id "+commentRequest.getCommentParent());
            }
            parentComment = parentCommentOptional.get();

        }

        Post post = null;
        if(commentRequest.getPostId() != null){
            post = postService.getPostByIdCheck(commentRequest.getPostId());
        }

        Comment comment = Comment.builder()
                .body(commentRequest.getBody())
                .commentParent(parentComment)
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);
        if(parentComment !=null){
            notificationService.generateCommentNotification(user,parentComment,commentRequest);
        }
    }

    public CommentResponse getCommentById(Long commentId){
        Comment comment = getCommentByIdWithCheck(commentId);
        return ModelMapper.convertCommentDTO(comment);
    }

    public List<CommentResponse> getAllCommentsOfPost(Long postId){
        Post post = postService.getPostByIdCheck(postId);

        return commentRepository.getAllCommentsOfPost(postId).stream().map(
                ModelMapper::convertCommentDTO
        ).toList();
    }

    public void deleteComment(Long commentId){
        Comment comment = getCommentByIdWithCheck(commentId);
        commentRepository.delete(comment);
    }

    public void updateComment(CommentRequest commentRequest, User user,Long commentId){
        Comment comment = getCommentByIdWithCheck(commentId);
        Comment parentComment = null;
        if(commentRequest.getCommentParent() !=null){
            Optional<Comment> parentCommentOptional = commentRepository.findById(commentRequest.getCommentParent());
            if(parentCommentOptional.isEmpty()){
                throw new NotFoundResponseException("There is no parent comment with id "+commentRequest.getCommentParent());
            }
            parentComment = parentCommentOptional.get();
        }
        Post post = null;
        if(commentRequest.getPostId() != null){
            post = postService.getPostByIdCheck(commentRequest.getPostId());
        }

        comment.setBody(commentRequest.getBody());
        comment.setCommentParent(parentComment);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }
}
