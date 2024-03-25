package com.example.productService.model.notification;

import com.example.productService.model.DateEntity;
import com.example.productService.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class Notification extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String message;
    @ElementCollection
    private List<Long> contentId;
}
