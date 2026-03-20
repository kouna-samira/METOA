package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox;

import java.util.List;

public interface InboxService {
    List<InboxDTO> getInbox(String userId);
}
