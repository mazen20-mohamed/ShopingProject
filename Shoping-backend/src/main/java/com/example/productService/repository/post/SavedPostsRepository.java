package com.example.productService.repository.post;

import com.example.productService.model.auth.User;
import com.example.productService.model.post.Post;
import com.example.productService.model.post.SavedPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SavedPostsRepository extends JpaRepository<SavedPosts,Long> {

    @Query("Select p.post from SavedPosts p Where p.user = :user")
    Page<Post> getAllPostsByUser(Pageable pageable, User user);
}
