package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.media.paid.InputPaidMedia;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.suggestedpost.SuggestedPostParameters;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.5
 *
 * Use this method to send paid media to channel chats. On success, the sent Message is returned.
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
public class SendPaidMedia extends PartialBotApiMethod<ArrayList<Message>> {
    public static final String PATH = "sendPaidMedia";

    public static final String CHAT_ID_FIELD = "chat_id";
    public static final String STAR_COUNT_FIELD = "star_count";
    public static final String MEDIA_FIELD = "media";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSE_MODE_FIELD = "parse_mode";
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";
    public static final String DISABLE_NOTIFICATION_FIELD = "disable_notification";
    public static final String PROTECT_CONTENT_FIELD = "protect_content";
    public static final String REPLY_PARAMETERS_FIELD = "reply_parameters";
    public static final String REPLY_MARKUP_FIELD = "reply_markup";
    public static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    public static final String PAYLOAD_FIELD = "payload";
    public static final String ALLOW_PAID_BROADCAST_FIELD = "allow_paid_broadcast";
    public static final String DIRECT_MESSAGES_TOPIC_ID_FIELD = "direct_messages_topic_id";
    public static final String SUGGESTED_POST_PARAMETERS_FIELD = "suggested_post_parameters";

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername).
     * If the chat is a channel, all Telegram Star proceeds from this media will be credited to the chat's balance.
     * Otherwise, they will be credited to the bot's balance.
     */
    @NonNull
    private String chatId;
    /**
     * Optional.
     * Identifier of the direct messages topic to which the message will be sent;
     * required if the message is sent to a direct messages chat
     */
    private Integer directMessagesTopicId;
    /**
     * The number of Telegram Stars that must be paid to buy access to the media; 1-25000
     */
    @NonNull
    private Integer starCount;
    /**
     * A JSON-serialized array describing the media to be sent; up to 10 items
     */
    @NonNull
    private List<InputPaidMedia> media;
    /**
     * Optional
     * Media caption, 0-1024 characters after entities parsing
     */
    private String caption;
    /**
     * Optional
     * Mode for parsing entities in the media caption. See formatting options for more details.
     */
    private String parseMode;
    /**
     * Optional
     * A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
     */
    private List<MessageEntity> captionEntities;
    /**
     *  Optional
     *  Pass True, if the caption must be shown above the message media
     */
    private Boolean showCaptionAboveMedia;
    /**
     * Optional
     * Sends the message silently. Users will receive a notification with no sound.
     */
    private Boolean disableNotification;
    /**
     * Optional
     * Protects the contents of the sent message from forwarding and saving
     */
    private Boolean protectContent;
    /**
     * Optional
     * Description of the message to reply to
     */
    private ReplyParameters replyParameters;
    /**
     * Optional
     * Additional interface options.
     * A JSON-serialized object for an inline keyboard, custom reply keyboard,
     * instructions to remove a reply keyboard or to force a reply from the user
     */
    private ReplyKeyboard replyMarkup;
    /**
     * Optional
     * Unique identifier of the business connection on behalf of which the message will be sent
     */
    private String businessConnectionId;
    /**
     * Optional
     * Bot-defined paid media payload, 0-128 bytes.
     * This will not be displayed to the user, use it for your internal processes.
     */
    private String payload;
    /**
     * Optional
     * Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars per message.
     * The relevant Stars will be withdrawn from the bot's balance
     */
    private Boolean allowPaidBroadcast;

    /**
     * Optional
     * A JSON-serialized object containing the parameters of the suggested post to send;
     * for direct messages chats only
     */
    private SuggestedPostParameters suggestedPostParameters;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public ArrayList<Message> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, Message.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredChatId(chatId, this);

        if (starCount == null || starCount < 1 || starCount > 25000) {
            throw new TelegramApiValidationException("StarCount must be between 1 and 25000", this);
        }

        if (media.isEmpty()) {
            throw new TelegramApiValidationException("Media parameter can't be empty", this);
        } else if (media.size() < 2 || media.size() > 10) {
            throw new TelegramApiValidationException("Number of media should be between 2 and 10", this);
        }

        for (InputPaidMedia inputMedia : media) {
            if (inputMedia == null) {
                throw new TelegramApiValidationException("Media parameter can not be empty", this);
            } else {
                inputMedia.validate();
            }
        }

        if (replyParameters != null) {
            replyParameters.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static abstract class SendPaidMediaBuilder<C extends SendPaidMedia, B extends SendPaidMediaBuilder<C, B>> extends PartialBotApiMethodBuilder<ArrayList<Message>, C, B> {
        @Tolerate
        public SendPaidMediaBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
