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
 * @version 4.0.0
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound).
 * On success, the sent Message is returned.
 *
 * Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
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
public class SendAnimation extends SendMediaBotMethod<Message> {
    public static final String PATH = "sendAnimation";

    public static final String ANIMATION_FIELD = "animation";
    public static final String DURATION_FIELD = "duration";
    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSE_MODE_FIELD = "parse_mode";
    public static final String THUMBNAIL_FIELD = "thumbnail";
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String HAS_SPOILER_FIELD = "has_spoiler";
    public static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    /**
     * Unique identifier for the chat to send the message to (Or username for channels)
     */
    @NonNull
    private String chatId;
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    private Integer messageThreadId;

    /**
     * Animation to send. Pass a file_id as String to send an animation that exists on the
     * Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation
     * from the Internet, or upload a new animation using multipart/form-data.
     */
    @NonNull
    private InputFile animation;
    /**
     * Optional.
     * Duration of sent animation in seconds
     */
    private Integer duration;
    /**
     * Optional.
     * Animation caption (may also be used when resending videos by file_id).
     */
    private String caption;
    /**
     * Optional.
     * Animation width
     */
    private Integer width;
    /**
     * Optional.
     * Animation height
     */
    private Integer height;
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
     * Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
     */
    private String parseMode;
    /**
     * Optional.
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumbnail;
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of parse_mode
     */
    @Singular
    private List<MessageEntity> captionEntities;
    /**
     * Optional.
     * Pass True, if the message should be sent even if the specified replied-to message is not found
     */
    private Boolean allowSendingWithoutReply;
    /**
     * Optional.
     * Protects the contents of sent messages from forwarding and saving
     */
    private Boolean protectContent;
    /**
     * Optional.
     * Pass True if the animation must be covered with a spoiler animation
     */
    private Boolean hasSpoiler;
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

        animation.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (thumbnail != null) {
            thumbnail.validate();
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
        return animation;
    }

    @Override
    public String getFileField() {
        return ANIMATION_FIELD;
    }
    
    public static abstract class SendAnimationBuilder<C extends SendAnimation, B extends SendAnimationBuilder<C, B>> extends SendMediaBotMethodBuilder<Message, C, B> {
        @Tolerate
        public SendAnimationBuilder<C, B> chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
