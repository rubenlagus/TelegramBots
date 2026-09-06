package org.telegram.telegrambots.meta.api.methods.send;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.ephemeral.EphemeralMessageParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.suggestedpost.SuggestedPostParameters;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class SendMediaBotMethod<T extends Serializable> extends PartialBotApiMethod<T> {
    public static final String CHAT_ID_FIELD = "chat_id";
    public static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    public static final String DIRECT_MESSAGES_TOPIC_ID_FIELD = "direct_messages_topic_id";
    public static final String REPLY_TO_MESSAGE_ID_FIELD = "reply_to_message_id";
    public static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    public static final String PROTECT_CONTENT_FIELD = "protect_content";
    public static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    public static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    public static final String REPLY_MARKUP_FIELD = "reply_markup";
    public static final String MESSAGE_EFFECT_ID_FIELD = "message_effect_id";
    public static final String ALLOW_PAID_BROADCAST_FIELD = "allow_paid_broadcast";
    public static final String SUGGESTED_POST_PARAMETERS_FIELD = "suggested_post_parameters";
    public static final String EPHEMERAL_MESSAGE_PARAMETERS_FIELD = "ephemeral_message_parameters";

    /**
     * @deprecated Bot API 10.3 replaced this parameter with {@code ephemeral_message_parameters}.
     */
    @Deprecated
    public static final String RECEIVER_USER_ID_FIELD = "receiver_user_id";
    /**
     * @deprecated Bot API 10.3 replaced this parameter with {@code ephemeral_message_parameters}.
     */
    @Deprecated
    public static final String CALLBACK_QUERY_ID_FIELD = "callback_query_id";

    public abstract String getChatId();

    public abstract Integer getMessageThreadId();

    public abstract Integer getDirectMessagesTopicId();

    /**
     * @deprecated Use {@link #getReplyParameters()} instead
     */
    @Deprecated
    public Integer getReplyToMessageId() {
        return getReplyParameters() != null ? getReplyParameters().getMessageId() : null;
    }

    public abstract Boolean getDisableNotification();

    /**
     * @deprecated Use {@link #getReplyParameters()} instead
     */
    @Deprecated
    public Boolean getAllowSendingWithoutReply() {
        return getReplyParameters() != null ? getReplyParameters().getAllowSendingWithoutReply() : null;
    }

    public abstract Boolean getProtectContent();

    public abstract InputFile getFile();

    public abstract String getFileField();

    public abstract ReplyParameters getReplyParameters();

    public abstract ReplyKeyboard getReplyMarkup();

    public abstract String getMessageEffectId();

    public abstract Boolean getAllowPaidBroadcast();

    public abstract SuggestedPostParameters getSuggestedPostParameters();

    /**
     * A JSON-serialized object containing the parameters of the ephemeral message to send.
     * Only supported by the methods that accept ephemeral messages; defaults to null otherwise.
     */
    public EphemeralMessageParameters getEphemeralMessageParameters() {
        return null;
    }

    /**
     * @deprecated Use {@link #getEphemeralMessageParameters()} instead
     */
    @Deprecated
    public Long getReceiverUserId() {
        return getEphemeralMessageParameters() != null ? getEphemeralMessageParameters().getReceiverUserId() : null;
    }

    /**
     * @deprecated Use {@link #getEphemeralMessageParameters()} instead
     */
    @Deprecated
    public String getCallbackQueryId() {
        return getEphemeralMessageParameters() != null ? getEphemeralMessageParameters().getCallbackQueryId() : null;
    }

    public static abstract class SendMediaBotMethodBuilder<T extends Serializable, C extends SendMediaBotMethod<T>, B extends SendMediaBotMethodBuilder<T, C, B>> extends PartialBotApiMethodBuilder<T, C, B> {

    }
}
