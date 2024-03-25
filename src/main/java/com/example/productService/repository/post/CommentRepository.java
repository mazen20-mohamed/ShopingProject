package com.example.productService.repository.post;

import com.example.productService.model.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository< Comment,Long> {

}
