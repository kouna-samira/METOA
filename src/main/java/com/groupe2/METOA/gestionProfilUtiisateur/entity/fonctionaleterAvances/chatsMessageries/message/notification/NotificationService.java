package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.notification;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepo notificationRepo;

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    public void sendNotification(User user, String msg, NotificationType type) {

        Notification notification = Notification.builder()
                .user(user)
                .message(msg)
                .type(type)
                . readStatus(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepo.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepo.findByUser_IdUserOrderByCreatedAtDesc(userId);
    }

    public long countUnread(String userId) {
        return notificationRepo.countByUser_IdUserAndReadStatusFalse(userId);
    }
}