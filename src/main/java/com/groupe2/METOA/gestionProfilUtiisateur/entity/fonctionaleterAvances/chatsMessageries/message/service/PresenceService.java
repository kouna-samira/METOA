package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service;


import java.time.LocalDateTime;

public interface PresenceService {
    void setUserOnlineStatus(String userId, boolean isOnline);

    void setUserOfflineStatus(String userId, LocalDateTime lastSeen);
}
