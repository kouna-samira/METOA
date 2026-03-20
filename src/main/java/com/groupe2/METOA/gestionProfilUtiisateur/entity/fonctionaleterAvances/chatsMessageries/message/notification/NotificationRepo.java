package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, String > {
    List<Notification> findByUser_IdUserOrderByCreatedAtDesc(String userId);

    long countByUser_IdUserAndReadStatusFalse(String userId);
}
