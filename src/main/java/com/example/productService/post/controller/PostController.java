package com.example.productService.post.controller;

import com.example.productService.config.CurrentUser;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.post.dto.PostRequest;
import com.example.productService.post.dto.PostResponse;
import com.example.productService.post.service.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/post")
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping("/{shopId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@Valid @ModelAttribute PostRequest postRequest,
                           @PathVariable Long shopId) throws IOException {
        postService.createPost(postRequest,shopId);
    }

    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId,
                                    @CurrentUser User user){
        return postService.getPostById(postId,user);
    }

    @PutMapping("update/{postId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void updatePost(@Valid @ModelAttribute PostRequest postRequest,
                           @PathVariable Long postId) throws IOException {
        postService.updatePost(postRequest,postId);
    }

    @DeleteMapping("delete/{postId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }

    @GetMapping("/photo/{shopName}/post{postId}/{fileName:.+}")
    public ResponseEntity<Resource> getPostImgOrVideo(@PathVariable Long postId,
                                                      @PathVariable String shopName ,
                                                      @PathVariable String fileName){
        return postService.getPostImgOrVideo(shopName,postId,fileName);
    }

    @GetMapping("increaseLike/{postId}")
    @PreAuthorize("hasRole('USER')")
    public void increaseLike(@PathVariable Long postId,
                             @CurrentUser User user) {
        postService.increaseLike(postId,user);
    }

    @GetMapping("decreaseLike/{postId}")
    @PreAuthorize("hasRole('USER')")
    public void decreaseLike(@PathVariable Long postId,
                             @CurrentUser User user) {
        postService.decreaseLike(postId,user);
    }

    @GetMapping("allPosts/{page}/{size}")
    public PagedResponse<PostResponse> getAllPosts(@PathVariable int page,
                                                   @PathVariable int size,
                                                   @CurrentUser User user) {
        return postService.getAllPosts(page,size,user);
    }

    @GetMapping("shop/{shopId}/{page}/{size}")
    public PagedResponse<PostResponse> getAllPostsOfShop(@PathVariable Long shopId,
                                                         @PathVariable int page,
                                                         @PathVariable int size,
                                                         @CurrentUser User user) {
        return postService.getAllPostsOfShop(user, shopId ,page,size);
    }
}
