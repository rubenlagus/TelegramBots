package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message.
 * For this to work, your audio must be in an .OGG file encoded with OPUS, or in .MP3 format, or in .M4A format
 * (other formats may be sent as Audio or Document).
 * On success, the sent Message is returned.
 * @apiNote Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendVoice extends SendMediaBotMethod<Message> {
    public static final String PATH = "sendVoice";

    public static final String VOICE_FIELD = "voice";
    public static final String DURATION_FIELD = "duration";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSE_MODE_FIELD = "parse_mode";
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";
    public static final String SHOW_CAPTION_ABOVE_MEDIA_FIELD = "show_caption_above_media";

    /**
     * Unique identifier for the chat sent message to (Or username for channels)
     */
    @NonNull
    private String chatId;
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    private Integer messageThreadId;
    /**
     * Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, 
     * or upload a new audio file using multipart/form-data.
     */
    @NonNull
    private InputFile voice;
    /**
     * Optional. 
     * Sends the message silently. Users will receive a notification with no sound.
     */
    private Boolean disableNotification;
    /**
     * Optional. 
     * If the message is a reply, ID of the original message
     */
    private Integer replyToMessageId;
    /**
     * Optional.
     * Additional interface options.
     * A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove a reply keyboard
     * or to force a reply from the user.
     */
    private ReplyKeyboard replyMarkup;
    /**
     * Optional. 
     * Duration of sent audio in seconds
     */
    private Integer duration;
    /**
     * Optional.
     * Voice caption (may also be used when resending videos by file_id).
     */
    private String caption;
    /**
     * Optional. 
     * Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
     */
    private String parseMode;
    /**
     * Optional. 
     * List of special entities that appear in the caption, which can be specified instead of parse_mode
     */
    @Singular
    private List<MessageEntity> captionEntities;
    /**
     * Optional	
     * Pass True, if the message should be sent even if the specified replied-to message is not found
     */
    private Boolean allowSendingWithoutReply;
    /**
     * Optional.
     * Protects the contents of sent messages from forwarding and saving
     */
    private Boolean protectContent;
    /**
     * Optional
     * Description of the message to reply to
     */
    private ReplyParameters replyParameters;
    /**
     * Optional.
     * Unique identifier of the business connection on behalf of which the message will be sent
     */
    private String businessConnectionId;
    /**
     * Optional
     * Unique identifier of the message effect to be added to the message
     */
    private String messageEffectId;
    /**
     * Optional.
     * Pass True, if the caption must be shown above the message media
     */
    private Boolean showCaptionAboveMedia;

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
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Message.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        voice.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public InputFile getFile() {
        return voice;
    }

    @Override
    public String getFileField() {
        return VOICE_FIELD;
    }

    public static abstract class SendVoiceBuilder<C extends SendVoice, B extends SendVoiceBuilder<C, B>> extends SendMediaBotMethodBuilder<Message, C, B> {
        @Tolerate
        public SendVoiceBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
