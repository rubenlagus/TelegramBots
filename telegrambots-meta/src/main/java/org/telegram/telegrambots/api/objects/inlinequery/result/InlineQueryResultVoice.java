package org.telegram.telegrambots.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a voice recording in an .ogg container encoded with OPUS. By default,
 * this voice recording will be sent by the user. Alternatively, you can use input_message_content
 * to send a message with the specified content instead of the the voice message.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultVoice implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String VOICEURL_FIELD = "voice_url";
    private static final String TITLE_FIELD = "title";
    private static final String VOICE_DURATION_FIELD = "voice_duration";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String CAPTION_FIELD = "caption";

    @JsonProperty(TYPE_FIELD)
    private final String type = "voice"; ///< Type of the result, must be "voice"
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(VOICEURL_FIELD)
    private String voiceUrl; ///< A valid URL for the voice recording
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Recording title
    @JsonProperty(VOICE_DURATION_FIELD)
    private Integer voiceDuration; ///< Optional. Recording duration in seconds
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the voice recording
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Voice caption (may also be used when resending documents by file_id), 0-200 characters

    public InlineQueryResultVoice() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultVoice setId(String id) {
        this.id = id;
        return this;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public InlineQueryResultVoice setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultVoice setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getVoiceDuration() {
        return voiceDuration;
    }

    public InlineQueryResultVoice setVoiceDuration(Integer voiceDuration) {
        this.voiceDuration = voiceDuration;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultVoice setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultVoice setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultVoice setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (voiceUrl == null || voiceUrl.isEmpty()) {
            throw new TelegramApiValidationException("VoiceUrl parameter can't be empty", this);
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
        return "InlineQueryResultVoice{" +
                "id='" + id + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", title='" + title + '\'' +
                ", voiceDuration=" + voiceDuration +
                ", inputMessageContent=" + inputMessageContent +
                ", replyMarkup=" + replyMarkup +
                ", caption='" + caption + '\'' +
                '}';
    }
}
