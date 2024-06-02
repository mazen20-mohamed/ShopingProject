package com.example.productService.repository.notification;

import com.example.productService.model.notification.ManagerNotification;
import com.example.productService.model.notification.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManagerNotificationRepository extends JpaRepository<ManagerNotification,Long> {

    @Query("SELECT p from ManagerNotification p where p.manager.id = :managerId")
    Page<ManagerNotification> findByManagerId(Long managerId, Pageable pageable);
}
