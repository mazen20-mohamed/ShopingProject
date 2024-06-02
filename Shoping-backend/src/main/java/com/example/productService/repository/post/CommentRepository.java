package com.example.productService.repository.post;

import com.example.productService.model.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository< Comment,Long> {

    @Query("Select c from Comment c WHERE c.post.Id = :postId AND c.commentParent = null")
    List<Comment> getAllCommentsOfPost(@Param("postId") Long postId);
}
