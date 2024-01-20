package org.telegram.telegrambots.meta.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to copy messages of any kind.
 * Service messages, giveaway messages, giveaway winners messages, and invoice messages can't be copied.
 * A quiz poll can be copied only if the value of the field correct_option_id is known to the bot.
 * The method is analogous to the method forwardMessage, but the copied message doesn't have a link
 * to the original message.
 *
 * Returns the MessageId of the message sent on success.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CopyMessage extends BotApiMethod<MessageId> {
    public static final String PATH = "copyMessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String FROMCHATID_FIELD = "from_chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String CAPTION_FIELD = "caption";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String CAPTIONENTITIES_FIELD = "caption_entities";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    private static final String REPLYMARKUP_FIELD = "reply_markup";
    private static final String PROTECTCONTENT_FIELD = "protect_content";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(FROMCHATID_FIELD)
    @NonNull
    private String fromChatId; ///< Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
    @JsonProperty(MESSAGEID_FIELD)
    @NonNull
    private Integer messageId; ///< Message identifier in the chat specified in from_chat_id
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Mode for parsing entities in the new caption. See formatting options for more details.
    @JsonProperty(CAPTIONENTITIES_FIELD)
    private List<MessageEntity> captionEntities; ///< Optional. List of special entities that appear in the new caption, which can be specified instead of parse_mode
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional. Pass True, if the message should be sent even if the specified replied-to message is not found
    /**
     * Optional.
     *
     * Additional interface options.
     * A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or
     * to force a reply from the user.
     */
    @JsonProperty(REPLYMARKUP_FIELD)
    @JsonDeserialize()
    private ReplyKeyboard replyMarkup;
    @JsonProperty(PROTECTCONTENT_FIELD)
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
    private ReplyParameters replyParameters;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    @Tolerate
    public void setFromChatId(@NonNull Long fromChatId) {
        this.fromChatId = fromChatId.toString();
    }

    public void enableNotification() {
        this.disableNotification = null;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    public void enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        } else {
            this.parseMode = null;
        }
    }

    public void enableHtml(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.HTML;
        } else {
            this.parseMode = null;
        }
    }

    public void enableMarkdownV2(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWNV2;
        } else {
            this.parseMode = null;
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public MessageId deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, MessageId.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static class CopyMessageBuilder {

        @Tolerate
        public CopyMessageBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }

        @Tolerate
        public CopyMessageBuilder fromChatId(@NonNull Long fromChatId) {
            this.fromChatId = fromChatId.toString();
            return this;
        }
    }
}
