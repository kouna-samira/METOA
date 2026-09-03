package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import org.springframework.data.domain.Page;

public interface InboxService {
    Page<InboxDTO> getInbox(String userId, int page, int size);
    InboxDTO getSingleInbox(String userId, String conversationId);
    void notifyInboxUpdate(MessageResDTO message);
}