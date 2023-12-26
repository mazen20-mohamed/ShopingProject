package com.example.productService.post;

import com.example.productService.post.dto.PostRequest;
import com.example.productService.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/add/{shopId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@ModelAttribute PostRequest postRequest,
                           @PathVariable Long shopId) throws IOException {
        postService.createPost(postRequest,shopId);
    }

    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId) throws IOException {
        return postService.getPostById(postId);
    }

    @PutMapping("update/{postId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void updatePost(@RequestBody PostRequest postRequest,
                           @PathVariable Long postId) throws IOException {
        postService.updatePost(postRequest,postId);
    }

    @DeleteMapping("delete/{postId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void deletePost(@PathVariable Long postId) throws IOException {
        postService.deletePost(postId);
    }

}
