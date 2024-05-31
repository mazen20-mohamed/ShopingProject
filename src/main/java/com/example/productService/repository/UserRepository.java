package com.example.productService.repository;

import com.example.productService.model.auth.Role;
import com.example.productService.model.auth.User;
import com.example.productService.model.notification.UserNotification;
import com.example.productService.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.role = ROLE_USER")
    Optional<List<User>> findAllUsers();

    @Query("SELECT e FROM User e WHERE e.firstname LIKE %:searchTerm%")
    List<User> findFirstTenByNameContaining(String searchTerm , Pageable pageable);

    @Query("select u from User u where u.role = ROLE_MANAGER AND enabledToCreateShop = false")
    Page<User> findAllManagersAndDisabled(Pageable pageable);
}
