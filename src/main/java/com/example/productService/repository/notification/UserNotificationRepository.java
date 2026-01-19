package com.example.productService.repository.notification;

import com.example.productService.model.notification.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserNotificationRepository extends JpaRepository<UserNotification,Long> {

    @Query("SELECT p from UserNotification p where p.user.id = :userId")
    Page<UserNotification> findByUserId(Long userId, Pageable pageable);
}
