package com.example.productService.repository.post;

import com.example.productService.model.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    @Query("SELECT l from Like l where l.user.Id = :userId AND l.post.Id = :postId")
    Optional<Like> findByUserAndPost(Long userId ,Long postId);
}
