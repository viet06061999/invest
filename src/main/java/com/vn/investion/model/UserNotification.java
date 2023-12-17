package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.NotificationStatus;
import com.vn.investion.model.define.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_noti")
public class UserNotification extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    String object;
    NotificationType type;
    NotificationStatus status = NotificationStatus.UNREAD;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}