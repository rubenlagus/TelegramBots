package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound).
 * On success, the sent Message is returned.
 *
 * Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
 */
public class SendAnimation extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendAnimation";

    public static final String CHATID_FIELD = "chat_id";
    public static final String ANIMATION_FIELD = "animation";
    public static final String DURATION_FIELD = "duration";
    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String THUMB_FIELD = "thumb";


    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Animation to send. Pass a file_id as String to send an animation that exists on the
     * Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation
     * from the Internet, or upload a new animation using multipart/form-data.
     */
    private InputFile animation;
    private Integer duration; ///< Optional. Duration of sent animation in seconds
    private String caption; ///< OptionaL. Animation caption (may also be used when resending videos by file_id).
    private Integer width; ///< Optional. Animation width
    private Integer height; ///< OptionaL. Animation height
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail‘s width and height should not exceed 90.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

    public SendAnimation() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public SendAnimation setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public InputFile getAnimation() {
        return animation;
    }

    public SendAnimation setAnimation(String animation) {
        this.animation = new InputFile(animation);
        return this;
    }

    public SendAnimation setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public SendAnimation setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public SendAnimation setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendAnimation setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendAnimation setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendAnimation enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendAnimation disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public SendAnimation setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public SendAnimation setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public SendAnimation setAnimation(File file) {
        this.animation = new InputFile(file, file.getName());
        return this;
    }

    public SendAnimation setAnimation(String animationName, InputStream inputStream) {
    	Objects.requireNonNull(animationName, "animationName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
        this.animation = new InputFile(inputStream, animationName);
        return this;
    }

    public SendAnimation setAnimation(InputFile animation) {
        Objects.requireNonNull(animation, "animation cannot be null!");
        this.animation = animation;
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public SendAnimation setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public SendAnimation setThumb(InputFile thumb) {
        this.thumb = thumb;
        return this;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending animation", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (animation == null) {
            throw new TelegramApiValidationException("Animation parameter can't be empty", this);
        }

        animation.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (thumb != null) {
            thumb.validate();
        }
    }

    @Override
    public String toString() {
        return "SendAnimation{" + "chatId='" + chatId + '\'' +
                ", animation=" + animation +
                ", duration=" + duration +
                ", caption='" + caption + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", parseMode='" + parseMode + '\'' +
                ", thumb=" + thumb +
                '}';
    }
}
