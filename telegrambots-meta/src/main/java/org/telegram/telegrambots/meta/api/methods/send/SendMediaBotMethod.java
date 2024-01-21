package org.telegram.telegrambots.meta.api.methods.send;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class SendMediaBotMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    public static final String CHAT_ID_FIELD = "chat_id";
    public static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    public static final String REPLY_TO_MESSAGE_ID_FIELD = "reply_to_message_id";
    public static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    public static final String PROTECT_CONTENT_FIELD = "protect_content";
    public static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    public static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    public static final String REPLY_MARKUP_FIELD = "reply_markup";

    public abstract String getChatId();

    public abstract Integer getMessageThreadId();

    public abstract Integer getReplyToMessageId();

    public abstract Boolean getDisableNotification();

    public abstract Boolean getAllowSendingWithoutReply();

    public abstract Boolean getProtectContent();

    public abstract InputFile getFile();

    public abstract String getFileField();

    public abstract ReplyParameters getReplyParameters();

    public abstract ReplyKeyboard getReplyMarkup();

    public static abstract class SendMediaBotMethodBuilder<T extends Serializable, C extends SendMediaBotMethod<T>, B extends SendMediaBotMethodBuilder<T, C, B>> extends PartialBotApiMethodBuilder<T, C, B> {
    }
}
