package com.example.productService.post.service;

import com.example.productService.model.auth.User;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.post.dto.PostRequest;
import com.example.productService.post.dto.PostResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.io.IOException;

public interface PostService {
    void createPost(PostRequest postRequest, Long shopId)  throws IOException;
    PostResponse getPostById(Long id, User user);
    void updatePost(PostRequest postRequest , Long postId) throws IOException;

    void deletePost(Long postId) throws IOException;
    void increaseLike(Long postId, User user);
    void decreaseLike(Long postId, User user);
    ResponseEntity<Resource> getPostImgOrVideo(
            String shopName
            , Long postId
            , String fileName);

    PagedResponse<PostResponse> getAllPosts(int page , int size , User user);
    PagedResponse<PostResponse> getAllPostsOfShop(User user,Long shopId , int page, int size);
}
