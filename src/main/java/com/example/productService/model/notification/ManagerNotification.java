package com.example.productService.model.notification;

import com.example.productService.model.auth.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notification_manager")
@Data
public class ManagerNotification extends Notification{
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Builder
    public ManagerNotification(Long id, NotificationType notificationType,
                               String message, List<Long> contentId, User manager) {
        super(id, notificationType, message, contentId);
        this.manager = manager;
    }
}
