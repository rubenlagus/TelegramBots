package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * Describes reply parameters for the message that is being sent.
 * @author Ruben Bermudez
 * @version 7.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyParameters implements BotApiObject, Validable {
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String ALLOW_SENDING_WITHOUT_REPLY_FIELD = "allow_sending_without_reply";
    private static final String QUOTE_PARSE_MODE_FIELD = "quote_parse_mode";
    private static final String QUOTE_FIELD = "quote";
    private static final String QUOTE_ENTITIES_FIELD = "quote_entities";
    private static final String QUOTE_POSITION_FIELD = "quote_position";

    /**
     * 	Identifier of the message that will be replied to in the current chat, or in the chat chat_id if it is specified
     */
    @NonNull
    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * If the message to be replied to is from a different chat, unique identifier for the chat or username of the channel (in the format @channelusername)
     */
    @JsonProperty(CHAT_ID_FIELD)
    private String chatId;

    /**
     * Optional.
     * Pass True if the message should be sent even if the specified message to be replied to is not found; can be used only for replies in the same chat and forum topic.
     */
    @JsonProperty(ALLOW_SENDING_WITHOUT_REPLY_FIELD)
    private Boolean allowSendingWithoutReply;
    /**
     * Optional.
     * Quoted part of the message to be replied to; 0-1024 characters after entities parsing.
     * The quote must be an exact substring of the message to be replied to, including bold, italic, underline, strikethrough, spoiler, and custom_emoji entities.
     * The message will fail to send if the quote isn't found in the original message.
     */
    @JsonProperty(QUOTE_FIELD)
    private String quote;
    /**
     *  Optional.
     *  Mode for parsing entities in the quote. See formatting options for more details.
     */
    @JsonProperty(QUOTE_PARSE_MODE_FIELD)
    private String quoteParseMode;
    /**
     *  Optional.
     *  A JSON-serialized list of special entities that appear in the quote.
     *  It can be specified instead of quote_parse_mode.
     */
    @JsonProperty(QUOTE_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> quoteEntities;
    /**
     *  Optional. Position of the quote in the original message in UTF-16 code units
     */
    @JsonProperty(QUOTE_POSITION_FIELD)
    private Integer quotePosition;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId != null && chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty string", this);
        }
    }

    public static class ReplyParametersBuilder {

        @Tolerate
        public ReplyParameters.ReplyParametersBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
