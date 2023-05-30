package org.telegram.telegrambots.meta.api.objects.chatmember;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ruben Bermudez
 * @version 5.3
 */
public class ChatMemberTest {
    @Test
    public void testDeserializationChatMember() throws TelegramApiRequestException {
        String text = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"status\": \"member\",\n" +
                "        \"user\": {\n" +
                "            \"id\": 123456,\n" +
                "            \"first_name\": \"FirstName\",\n" +
                "            \"last_name\": \"LastName\",\n" +
                "            \"username\": \"UserName\",\n" +
                "            \"language_code\": \"en\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        ChatMember chatMember = new GetChatMember().deserializeResponse(text);
        assertEquals("member", chatMember.getStatus());
        ChatMemberMember chatMemberMember = (ChatMemberMember) chatMember;
        assertEquals(getUser(), chatMemberMember.getUser());
    }

    @Test
    public void testDeserializationChatMemberLeft() throws TelegramApiRequestException {
        String text = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"status\": \"left\",\n" +
                "        \"user\": {\n" +
                "            \"id\": 123456,\n" +
                "            \"first_name\": \"FirstName\",\n" +
                "            \"last_name\": \"LastName\",\n" +
                "            \"username\": \"UserName\",\n" +
                "            \"language_code\": \"en\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        ChatMember chatMember = new GetChatMember().deserializeResponse(text);
        assertEquals("left", chatMember.getStatus());
        ChatMemberLeft chatMemberLeft = (ChatMemberLeft) chatMember;
        assertEquals(getUser(), chatMemberLeft.getUser());
    }

    @Test
    public void testDeserializationChatMemberBanned() throws TelegramApiRequestException {
        String text = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"status\": \"kicked\",\n" +
                "        \"user\": {\n" +
                "            \"id\": 123456,\n" +
                "            \"first_name\": \"FirstName\",\n" +
                "            \"last_name\": \"LastName\",\n" +
                "            \"username\": \"UserName\",\n" +
                "            \"language_code\": \"en\"\n" +
                "        },\n" +
                "        \"until_date\": 10\n" +
                "    }\n" +
                "}";
        ChatMember chatMember = new GetChatMember().deserializeResponse(text);
        assertEquals("kicked", chatMember.getStatus());
        ChatMemberBanned chatMemberBanned = (ChatMemberBanned) chatMember;
        assertEquals(getUser(), chatMemberBanned.getUser());
        assertEquals(10, chatMemberBanned.getUntilDate());
    }

    @Test
    public void testDeserializationChatMemberOwner() throws TelegramApiRequestException {
        String text = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"status\": \"creator\",\n" +
                "        \"user\": {\n" +
                "            \"id\": 123456,\n" +
                "            \"first_name\": \"FirstName\",\n" +
                "            \"last_name\": \"LastName\",\n" +
                "            \"username\": \"UserName\",\n" +
                "            \"language_code\": \"en\"\n" +
                "        },\n" +
                "        \"custom_title\": \"CustomTitle\",\n" +
                "        \"is_anonymous\": true\n" +
                "    }\n" +
                "}";
        ChatMember chatMember = new GetChatMember().deserializeResponse(text);
        assertEquals("creator", chatMember.getStatus());
        ChatMemberOwner chatMemberOwner = (ChatMemberOwner) chatMember;
        assertEquals(getUser(), chatMemberOwner.getUser());
        assertEquals("CustomTitle", chatMemberOwner.getCustomTitle());
        assertEquals(true, chatMemberOwner.getIsAnonymous());
    }

    @Test
    public void testDeserializationChatMemberAdministrator() throws TelegramApiRequestException {
        String text = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"status\": \"administrator\",\n" +
                "        \"user\": {\n" +
                "            \"id\": 123456,\n" +
                "            \"first_name\": \"FirstName\",\n" +
                "            \"last_name\": \"LastName\",\n" +
                "            \"username\": \"UserName\",\n" +
                "            \"language_code\": \"en\"\n" +
                "        },\n" +
                "        \"can_be_edited\": true,\n" +
                "        \"custom_title\": \"CustomTitle\",\n" +
                "        \"is_anonymous\": true,\n" +
                "        \"can_manage_chat\": true,\n" +
                "        \"can_post_messages\": true,\n" +
                "        \"can_edit_messages\": true,\n" +
                "        \"can_delete_messages\": true,\n" +
                "        \"can_manage_video_chats\": true,\n" +
                "        \"can_restrict_members\": true,\n" +
                "        \"can_promote_members\": true,\n" +
                "        \"can_change_info\": true,\n" +
                "        \"can_invite_users\": true,\n" +
                "        \"can_pin_messages\": true\n" +
                "    }\n" +
                "}";
        ChatMember chatMember = new GetChatMember().deserializeResponse(text);
        assertEquals("administrator", chatMember.getStatus());
        ChatMemberAdministrator chatMemberAdministrator = (ChatMemberAdministrator) chatMember;
        assertEquals(getUser(), chatMemberAdministrator.getUser());
        assertEquals("CustomTitle", chatMemberAdministrator.getCustomTitle());
        assertEquals(true, chatMemberAdministrator.getIsAnonymous());
        assertEquals(true, chatMemberAdministrator.getCanBeEdited());
        assertEquals(true, chatMemberAdministrator.getCanManageChat());
        assertEquals(true, chatMemberAdministrator.getCanPostMessages());
        assertEquals(true, chatMemberAdministrator.getCanEditMessages());
        assertEquals(true, chatMemberAdministrator.getCanDeleteMessages());
        assertEquals(true, chatMemberAdministrator.getCanManageVideoChats());
        assertEquals(true, chatMemberAdministrator.getCanRestrictMembers());
        assertEquals(true, chatMemberAdministrator.getCanPromoteMembers());
        assertEquals(true, chatMemberAdministrator.getCanChangeInfo());
        assertEquals(true, chatMemberAdministrator.getCanInviteUsers());
        assertEquals(true, chatMemberAdministrator.getCanPinMessages());
    }

    @Test
    public void testDeserializationChatMemberRestricted() throws TelegramApiRequestException {
        String text = "{\n" +
                "    \"ok\": true,\n" +
                "    \"result\": {\n" +
                "        \"status\": \"restricted\",\n" +
                "        \"user\": {\n" +
                "            \"id\": 123456,\n" +
                "            \"first_name\": \"FirstName\",\n" +
                "            \"last_name\": \"LastName\",\n" +
                "            \"username\": \"UserName\",\n" +
                "            \"language_code\": \"en\"\n" +
                "        },\n" +
                "        \"is_anonymous\": true,\n" +
                "        \"can_change_info\": true,\n" +
                "        \"can_invite_users\": true,\n" +
                "        \"can_pin_messages\": true,\n" +
                "        \"is_member\": true,\n" +
                "        \"can_send_messages\": true,\n" +
                "        \"can_send_audios\": true,\n" +
                "        \"can_send_polls\": true,\n" +
                "        \"can_send_other_messages\": true,\n" +
                "        \"can_add_web_page_previews\": true,\n" +
                "        \"until_date\": 100\n" +
                "    }\n" +
                "}";
        ChatMember chatMember = new GetChatMember().deserializeResponse(text);
        assertEquals("restricted", chatMember.getStatus());
        ChatMemberRestricted chatMemberRestricted = (ChatMemberRestricted) chatMember;
        assertEquals(getUser(), chatMemberRestricted.getUser());
        assertEquals(true, chatMemberRestricted.getCanChangeInfo());
        assertEquals(true, chatMemberRestricted.getCanInviteUsers());
        assertEquals(true, chatMemberRestricted.getCanPinMessages());
        assertEquals(true, chatMemberRestricted.getCanSendMessages());
        assertEquals(true, chatMemberRestricted.getCanSendAudios());
        assertEquals(true, chatMemberRestricted.getCanSendPolls());
        assertEquals(true, chatMemberRestricted.getCanSendOtherMessages());
        assertEquals(true, chatMemberRestricted.getCanAddWebpagePreviews());
        assertEquals(100, chatMemberRestricted.getUntilDate());
    }


    private User getUser() {
        User user = new User();
        user.setId(123456L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setUserName("UserName");
        user.setLanguageCode("en");
        return user;
    }
}
