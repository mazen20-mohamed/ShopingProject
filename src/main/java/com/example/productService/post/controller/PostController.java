package com.example.productService.post.controller;

import com.example.productService.config.CurrentUser;
import com.example.productService.exception.ErrorResponse;
import com.example.productService.exception.NotFoundResponseException;
import com.example.productService.model.auth.User;
import com.example.productService.post.dto.PagedResponse;
import com.example.productService.post.dto.PostRequest;
import com.example.productService.post.dto.PostResponse;
import com.example.productService.post.service.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Post")
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping("/{shopId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "400 Bad Request",
                    description = "if there is error with input missing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void createPost(@Valid @ModelAttribute PostRequest postRequest,
                           @PathVariable Long shopId) throws IOException {
        postService.createPost(postRequest,shopId);
    }

    @Operation(summary = "get post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId,
                                    @CurrentUser User user){
        return postService.getPostById(postId,user);
    }

    @Operation(summary = "update post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void updatePost(@Valid @ModelAttribute PostRequest postRequest,
                           @PathVariable Long postId) throws IOException {
        postService.updatePost(postRequest,postId);
    }

    @Operation(summary = "delete post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }

    @Operation(summary = "get post images",description = "This endpoint be used with get post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("/photo/{shopName}/post{postId}/{fileName:.+}")
    public ResponseEntity<Resource> getPostImgOrVideo(@PathVariable Long postId,
                                                      @PathVariable String shopName ,
                                                      @PathVariable String fileName){
        return postService.getPostImgOrVideo(shopName,postId,fileName);
    }
    @Operation(summary = "like a post by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "400 Bad Request",
                    description = "if user is already make like before",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PutMapping("/like/{postId}")
    @PreAuthorize("hasRole('USER')")
    public void increaseLike(@PathVariable Long postId,
                             @CurrentUser User user) {
        postService.increaseLike(postId,user);
    }
    @Operation(summary = "unlike a post by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "400 Bad Request",
                    description = "if user has not make like before",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PutMapping("/unlike/{postId}")
    @PreAuthorize("hasRole('USER')")
    public void decreaseLike(@PathVariable Long postId,
                             @CurrentUser User user) {
        postService.decreaseLike(postId,user);
    }

    @Operation(summary = "get all posts for a user", description = "this used for the timeline page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("allPosts/{page}/{size}")
    public PagedResponse<PostResponse> getAllPosts(@PathVariable int page,
                                                   @PathVariable int size,
                                                   @CurrentUser User user) {
        return postService.getAllPosts(page,size,user);
    }
    @Operation(summary = "get all posts for a shop",
            description = "this used for the get all posts of a shop by shop Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "shop id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping("shopPosts/{shopId}/{page}/{size}")
    public PagedResponse<PostResponse> getAllPostsOfShop(@PathVariable Long shopId,
                                                         @PathVariable int page,
                                                         @PathVariable int size,
                                                         @CurrentUser User user) {
        return postService.getAllPostsOfShop(user, shopId ,page,size);
    }
}
