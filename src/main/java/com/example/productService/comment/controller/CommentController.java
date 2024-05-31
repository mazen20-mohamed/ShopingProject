package com.example.productService.comment.controller;

import com.example.productService.comment.dto.CommentRequest;
import com.example.productService.comment.dto.CommentResponse;
import com.example.productService.comment.service.CommentService;
import com.example.productService.config.CurrentUser;
import com.example.productService.exception.ErrorResponse;
import com.example.productService.model.auth.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/comment")
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;
    /*
    * This endpoint is for adding new comment
    */

    @PostMapping
    @Operation(summary = "add Comment to a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "Parent comment id or post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void addComment(@RequestBody CommentRequest commentRequest,
                           @CurrentUser User user){
        commentService.createComment(commentRequest,user);
    }

    /*
     * This endpoint is for get comment By I'd
     */

    @GetMapping("/{commentId}")
    @Operation(summary = "get Comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class))}),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "comment id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public CommentResponse getCommentById(@PathVariable @Min(1) Long commentId){
        return commentService.getCommentById(commentId);
    }
    /*
     * This endpoint is for getting all comments for post
     */
    @GetMapping("/getAllComments/{postId}")
    @Operation(summary = "get all Comment of a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "array",implementation = CommentResponse.class))}),
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
    public List<CommentResponse> getAllCommentsOfPost(@PathVariable @Min(1) Long postId){
        return commentService.getAllCommentsOfPost(postId);
    }
    /*
     * This endpoint is for deleting comment by its id
     */
    @DeleteMapping("/{commentId}")
    @Operation(summary = "delete Comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "comment id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void deleteComment(@PathVariable @Min(1) Long commentId){
        commentService.deleteComment(commentId);
    }

    /*
     * This endpoint is for updating comment
     */
    @PutMapping("/{commentId}")
    @Operation(summary = "delete Comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK",
                    description = "Everything OK"),
            @ApiResponse(responseCode = "404 Not Found",
                    description = "Parent comment id or post id is not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500 Internal Server Error",
                    description = "Error at backend side",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    public void updateComment(@CurrentUser User user,
                              @RequestBody CommentRequest commentRequest,
                                  @PathVariable @Min(1) Long commentId){
        commentService.updateComment(commentRequest,user,commentId);
    }
}
