package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.api.objects.suggestedpost.SuggestedPostParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Use this method to send rich messages. On success, the sent Message is returned.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendRichMessage extends BotApiMethodMessage {
    public static final String PATH = "sendRichMessage";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String DIRECT_MESSAGES_TOPIC_ID_FIELD = "direct_messages_topic_id";
    private static final String RICH_MESSAGE_FIELD = "rich_message";
    private static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    private static final String PROTECT_CONTENT_FIELD = "protect_content";
    private static final String ALLOW_PAID_BROADCAST_FIELD = "allow_paid_broadcast";
    private static final String MESSAGE_EFFECT_ID_FIELD = "message_effect_id";
    private static final String SUGGESTED_POST_PARAMETERS_FIELD = "suggested_post_parameters";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    /**
     * Optional. Unique identifier of the business connection on behalf of which the message will be sent
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    private String businessConnectionId;

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId;

    /**
     * Optional. Unique identifier for the target message thread (topic) of a forum
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;

    /**
     * Optional. Identifier of the direct messages topic to which the message will be sent
     */
    @JsonProperty(DIRECT_MESSAGES_TOPIC_ID_FIELD)
    private Integer directMessagesTopicId;

    /**
     * The message to be sent
     */
    @JsonProperty(RICH_MESSAGE_FIELD)
    @NonNull
    private InputRichMessage richMessage;

    /**
     * Optional. Sends the message silently.
     */
    @JsonProperty(DISABLE_NOTIFICATION_FIELD)
    private Boolean disableNotification;

    /**
     * Optional. Protects the contents of the sent message from forwarding and saving
     */
    @JsonProperty(PROTECT_CONTENT_FIELD)
    private Boolean protectContent;

    /**
     * Optional. Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee
     */
    @JsonProperty(ALLOW_PAID_BROADCAST_FIELD)
    private Boolean allowPaidBroadcast;

    /**
     * Optional. Unique identifier of the message effect to be added to the message
     */
    @JsonProperty(MESSAGE_EFFECT_ID_FIELD)
    private String messageEffectId;

    /**
     * Optional. A JSON-serialized object containing the parameters of the suggested post to send
     */
    @JsonProperty(SUGGESTED_POST_PARAMETERS_FIELD)
    private SuggestedPostParameters suggestedPostParameters;

    /**
     * Optional. Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;

    /**
     * Optional. Additional interface options.
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private ReplyKeyboard replyMarkup;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);
        if (richMessage == null) {
            throw new TelegramApiValidationException("RichMessage parameter can't be null", this);
        }
        richMessage.validate();
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static abstract class SendRichMessageBuilder<C extends SendRichMessage, B extends SendRichMessageBuilder<C, B>> extends BotApiMethodMessageBuilder<C, B> {
        @Tolerate
        public SendRichMessageBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
