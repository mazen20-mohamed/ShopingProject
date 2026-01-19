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
@Table(name = "notification_user")
@Data
public class UserNotification extends Notification {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserNotification(Long id, NotificationType notificationType, String message, List<Long> contentId,User user) {
        super(id, notificationType, message, contentId);
        this.user = user;
    }
}
