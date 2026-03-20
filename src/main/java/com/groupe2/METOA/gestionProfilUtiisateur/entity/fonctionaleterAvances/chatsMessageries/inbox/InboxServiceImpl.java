package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo.ConversationRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.repo.MessageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboxServiceImpl implements InboxService {

    private final ConversationRepo conversationRepo;
    private final MessageRepo messageRepo;

    public InboxServiceImpl(ConversationRepo conversationRepo, MessageRepo messageRepo) {
        this.conversationRepo = conversationRepo;
        this.messageRepo = messageRepo;
    }

    @Override
    public List<InboxDTO> getInbox(String userId) {

        List<Conversation> conversations = conversationRepo.findAll();

        return conversations.stream()
                .filter(c -> c.getUser1().getIdUser().equals(userId)
                        || c.getUser2().getIdUser().equals(userId))
                .map(c -> {

                    List<Message> messages =
                            messageRepo.findByConversation_ConversationIdOrderBySentAtAsc(c.getConversationId());

                    Message last = messages.isEmpty() ? null : messages.get(messages.size() - 1);

                    String otherUser = c.getUser1().getIdUser().equals(userId)
                            ? c.getUser2().getIdUser()
                            : c.getUser1().getIdUser();

                    long unread = messageRepo
                            .countByConversation_ConversationIdAndReceiver_IdUserAndStatus(
                                    c.getConversationId(),
                                    userId,
                                    MessageStatus.ENVOYE
                            );

                    return InboxDTO.builder()
                            .conversationId(c.getConversationId())
                            .otherUserId(otherUser)
                            .lastMessage(last != null ? last.getContent() : null)
                            .lastMessageDate(last != null ? last.getSentAt() : null)
                            .unreadCount(unread)
                            .build();
                })
                .toList();
    }
}