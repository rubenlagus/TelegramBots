package org.telegram.telegrambots.api.objects.inlinequery.result.chached;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a voice message stored on the Telegram servers. By default, this
 * voice message will be sent by the user. Alternatively, you can use input_message_content to send
 * a message with the specified content instead of the voice message.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedVoice implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String VOICE_FILE_ID_FIELD = "voice_file_id";
    private static final String TITLE_FIELD = "title";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String CAPTION_FIELD = "caption";

    @JsonProperty(TYPE_FIELD)
    private final String type = "voice"; ///< Type of the result, must be "voice"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(VOICE_FILE_ID_FIELD)
    private String voiceFileId; ///< A valid file identifier for the voice message
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Recording title
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the voice recording
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Voice caption (may also be used when resending documents by file_id), 0-200 characters

    public InlineQueryResultCachedVoice() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedVoice setId(String id) {
        this.id = id;
        return this;
    }

    public String getVoiceFileId() {
        return voiceFileId;
    }

    public InlineQueryResultCachedVoice setVoiceFileId(String voiceFileId) {
        this.voiceFileId = voiceFileId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedVoice setTitle(String title) {
        this.title = title;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultCachedVoice setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedVoice setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedVoice setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (voiceFileId == null || voiceFileId.isEmpty()) {
            throw new TelegramApiValidationException("VoiceFileId parameter can't be empty", this);
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
        return "InlineQueryResultCachedVoice{" +
                "id='" + id + '\'' +
                ", voiceFileId='" + voiceFileId + '\'' +
                ", title='" + title + '\'' +
                ", inputMessageContent=" + inputMessageContent +
                ", replyMarkup=" + replyMarkup +
                ", caption='" + caption + '\'' +
                '}';
    }
}
