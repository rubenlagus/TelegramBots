package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.groupadministration.PromoteChatMember;
import org.telegram.telegrambots.meta.api.objects.adminrights.ChatAdministratorRights;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.3
 */
public class TestCanSendWelcomeMessages {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    /**
     * All 8 @NonNull rights must be present or Jackson throws ValueInstantiationException
     * before the assertion is reached.
     */
    @Test
    public void testChatAdministratorRightsDeserializes() throws IOException {
        String json = "{"
                + "\"is_anonymous\":false,"
                + "\"can_manage_chat\":true,"
                + "\"can_delete_messages\":true,"
                + "\"can_manage_video_chats\":true,"
                + "\"can_restrict_members\":true,"
                + "\"can_promote_members\":false,"
                + "\"can_change_info\":true,"
                + "\"can_invite_users\":true,"
                + "\"can_send_welcome_messages\":true"
                + "}";

        ChatAdministratorRights rights = mapper.readValue(json, ChatAdministratorRights.class);

        assertTrue(rights.getCanSendWelcomeMessages());
    }

    /**
     * The new right is optional: an otherwise-complete payload without it must still deserialize,
     * leaving the field null rather than false.
     */
    @Test
    public void testChatAdministratorRightsOmitsWelcomeMessagesWhenAbsent() throws IOException {
        String json = "{"
                + "\"is_anonymous\":false,"
                + "\"can_manage_chat\":true,"
                + "\"can_delete_messages\":true,"
                + "\"can_manage_video_chats\":true,"
                + "\"can_restrict_members\":true,"
                + "\"can_promote_members\":false,"
                + "\"can_change_info\":true,"
                + "\"can_invite_users\":true"
                + "}";

        ChatAdministratorRights rights = mapper.readValue(json, ChatAdministratorRights.class);

        assertNull(rights.getCanSendWelcomeMessages());
    }

    @Test
    public void testChatMemberAdministratorDeserializes() throws IOException {
        String json = "{\"status\":\"administrator\",\"can_send_welcome_messages\":true}";

        ChatMemberAdministrator admin = mapper.readValue(json, ChatMemberAdministrator.class);

        assertTrue(admin.getCanSendWelcomeMessages());
    }

    @Test
    public void testPromoteChatMemberSerializes() throws IOException {
        PromoteChatMember method = PromoteChatMember.builder()
                .chatId(1L)
                .userId(2L)
                .canSendWelcomeMessages(true)
                .build();

        assertTrue(mapper.writeValueAsString(method).contains("\"can_send_welcome_messages\":true"));
    }

    @Test
    public void testPromoteChatMemberOmitsWhenUnset() throws IOException {
        PromoteChatMember method = PromoteChatMember.builder()
                .chatId(1L)
                .userId(2L)
                .build();

        assertFalse(mapper.writeValueAsString(method).contains("can_send_welcome_messages"));
    }
}
