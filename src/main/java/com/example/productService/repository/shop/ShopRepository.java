package com.example.productService.repository.shop;

import com.example.productService.model.auth.User;
import com.example.productService.model.post.Post;
import com.example.productService.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    @Query("SELECT e FROM Shop e WHERE e.name LIKE %:searchTerm% AND e.enabled = true")
    List<Shop> findFirstTenByNameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT e FROM Shop e WHERE e.enabled = true")
    Page<Shop> findAllShops(Pageable pageable);

    @Query("SELECT e FROM Shop e WHERE e.enabled = false")
    Page<Shop> findAllDisabledShops(Pageable pageable);

    @Query("SELECT COUNT(f) > 0 FROM Shop e JOIN e.followers f WHERE e.enabled = true AND e.id = :shopId AND f.id = :userId")
    boolean checkIfUserFollowShop(@Param("userId")Long userId,@Param("shopId")Long shopId);
}
