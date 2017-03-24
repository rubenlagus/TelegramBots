package org.telegram.telegrambots.api.objects.inlinequery.result;



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


    private final String type = "voice"; ///< Type of the result, must be "voice"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String voice_url; ///< A valid URL for the voice recording

    private String title; ///< Recording title

    private Integer voice_duration; ///< Optional. Recording duration in seconds

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the voice recording

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

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
        return voice_url;
    }

    public InlineQueryResultVoice setVoiceUrl(String voiceUrl) {
        this.voice_url = voiceUrl;
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
        return voice_duration;
    }

    public InlineQueryResultVoice setVoiceDuration(Integer voiceDuration) {
        this.voice_duration = voiceDuration;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultVoice setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultVoice setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
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
        if (voice_url == null || voice_url.isEmpty()) {
            throw new TelegramApiValidationException("VoiceUrl parameter can't be empty", this);
        }
        if (input_message_content != null) {
            input_message_content.validate();
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultVoice{" +
                "id='" + id + '\'' +
                ", voiceUrl='" + voice_url + '\'' +
                ", title='" + title + '\'' +
                ", voiceDuration=" + voice_duration +
                ", inputMessageContent=" + input_message_content +
                ", replyMarkup=" + reply_markup +
                ", caption='" + caption + '\'' +
                '}';
    }
}
