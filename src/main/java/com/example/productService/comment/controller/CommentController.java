package com.example.productService.comment.controller;

import com.example.productService.comment.dto.CommentRequest;
import com.example.productService.comment.dto.CommentResponse;
import com.example.productService.comment.service.CommentService;
import com.example.productService.config.CurrentUser;
import com.example.productService.model.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    /*
    * This endpoint is for adding new comment
    */

    @PostMapping("/add")
    public void addComment(@RequestBody CommentRequest commentRequest,
                           @CurrentUser User user){
        commentService.createComment(commentRequest,user);
    }

    /*
     * This endpoint is for get comment By I'd
     */

    @GetMapping("/{commentId}")
    public CommentResponse getCommentById(@PathVariable Long commentId){
        return commentService.getCommentById(commentId);
    }
    /*
     * This endpoint is for getting all comments for post
     */
    @GetMapping("/getAllComments/{postId}")
    public List<CommentResponse> getAllCommentsOfPost(@PathVariable Long postId){
        return commentService.getAllCommentsOfPost(postId);
    }
    /*
     * This endpoint is for deleting comment by its id
     */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }

    /*
     * This endpoint is for updating comment
     */
    @PutMapping("/{commentId}")
    public void updateComment(@CurrentUser User user,
                              @RequestBody CommentRequest commentRequest,
                              @PathVariable  Long commentId){
        commentService.updateComment(commentRequest,user,commentId);
    }
}
