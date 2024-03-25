package com.example.productService.repository.post;

import com.example.productService.model.post.Post;
import com.example.productService.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p from Post p where p.shop.Id in :followingUsersIds AND p.shop.enabled = true")
    Page<Post> findAllPostsByFollowedUser(@Param("followingUsersIds")List<Long> followingIdsList,Pageable pageable);

    @Query("SELECT p from Post p where p.shop.Id = :shopId AND p.shop.enabled = true")
    Page<Post> getPostsOfShop(@Param("shopId")Long shopId,Pageable pageable);

}
