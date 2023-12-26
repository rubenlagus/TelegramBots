package org.telegram.telegrambots.meta.api.methods.send;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.Serializable;

public abstract class SendMediaBotMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    public static String CHATID_FIELD = "chat_id";
    public static String MESSAGETHREADID_FIELD = "message_thread_id";
    public static String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static String PROTECTCONTENT_FIELD = "protect_content";
    public static String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";

    public abstract String getChatId();
    public abstract Integer getMessageThreadId();
    public abstract Integer getReplyToMessageId();
    public abstract Boolean getDisableNotification();
    public abstract Boolean getAllowSendingWithoutReply();
    public abstract Boolean getProtectContent();
    public abstract InputFile getFile();
    public abstract String getFileField();
}
