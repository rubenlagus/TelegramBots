package org.telegram.telegrambots.api.objects.inlinequery.result.chached;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a sticker stored on the Telegram servers. By default, this sticker
 * will be sent by the user. Alternatively, you can use input_message_content to send a message with
 * the specified content instead of the sticker.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedSticker implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String STICKER_FILE_ID_FIELD = "sticker_file_id";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "sticker"; ///< Type of the result, must be "sticker"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(STICKER_FILE_ID_FIELD)
    private String stickerFileId; ///< A valid file identifier of the sticker
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the sticker
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultCachedSticker() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedSticker setId(String id) {
        this.id = id;
        return this;
    }

    public String getStickerFileId() {
        return stickerFileId;
    }

    public InlineQueryResultCachedSticker setStickerFileId(String stickerFileId) {
        this.stickerFileId = stickerFileId;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultCachedSticker setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedSticker setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (stickerFileId == null || stickerFileId.isEmpty()) {
            throw new TelegramApiValidationException("StickerFileId parameter can't be empty", this);
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
        return "InlineQueryResultCachedSticker{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", sticker_file_id='" + stickerFileId + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
