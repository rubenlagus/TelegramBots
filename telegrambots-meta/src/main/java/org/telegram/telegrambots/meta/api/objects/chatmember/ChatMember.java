package org.telegram.telegrambots.meta.api.objects.chatmember;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.serialization.ChatMemberDeserializer;

/**
 * @author Ruben Bermudez
 * @version 5.3
 *
 * This object contains information about one member of a chat. Currently, the following 6
 * types of chat members are supported:
 */
@JsonDeserialize(using = ChatMemberDeserializer.class)
public interface ChatMember extends BotApiObject {
    String getStatus();
    User getUser();
}
