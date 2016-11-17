package org.telegram.telegrambots.api.objects.inlinequery.result.chached;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to an animated GIF file stored on the Telegram servers. By default, this
 * animated GIF file will be sent by the user with an optional caption. Alternatively, you can use
 * input_message_content to send a message with specified content instead of the animation.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedGif implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String GIF_FILE_ID_FIELD = "gif_file_id";
    private static final String TITLE_FIELD = "title";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "gif"; ///< Type of the result, must be "gif"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(GIF_FILE_ID_FIELD)
    private String gifFileId; ///< A valid file identifier for the GIF file
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the GIF file to be sent
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the GIF animation
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultCachedGif() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedGif setId(String id) {
        this.id = id;
        return this;
    }

    public String getGifFileId() {
        return gifFileId;
    }

    public InlineQueryResultCachedGif setGifFileId(String gifFileId) {
        this.gifFileId = gifFileId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedGif setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedGif setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultCachedGif setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedGif setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (gifFileId == null || gifFileId.isEmpty()) {
            throw new TelegramApiValidationException("GifFileId parameter can't be empty", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultCachedGif{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", gifUrl='" + gifFileId + '\'' +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
