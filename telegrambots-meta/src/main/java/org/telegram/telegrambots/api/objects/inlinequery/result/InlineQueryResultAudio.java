package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to an mp3 audio file. By default, this audio file will be sent by the
 * user. Alternatively, you can use input_message_content to send a message with the specified
 * content instead of the audio.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultAudio implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String AUDIOURL_FIELD = "audio_url";
    private static final String TITLE_FIELD = "title";
    private static final String PERFORMER_FIELD = "performer";
    private static final String AUDIO_DURATION_FIELD = "audio_duration";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String CAPTION_FIELD = "caption";


    private final String type = "audio"; ///< Type of the result, must be "audio"

    private String id; ///< Unique identifier of this result

    private String audio_url; ///< A valid URL for the audio file

    private String title; ///< Optional. Title for the result

    private String performer; ///< Optional. Performer

    private Integer audio_duration; ///< Optional. Audio duration in seconds

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the audio

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private String caption; ///< Optional. Audio caption (may also be used when resending documents by file_id), 0-200 characters

    public InlineQueryResultAudio() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultAudio setId(String id) {
        this.id = id;
        return this;
    }

    public String getAudioUrl() {
        return audio_url;
    }

    public InlineQueryResultAudio setAudioUrl(String audioUrl) {
        this.audio_url = audioUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultAudio setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPerformer() {
        return performer;
    }

    public InlineQueryResultAudio setPerformer(String performer) {
        this.performer = performer;
        return this;
    }

    public Integer getAudioDuration() {
        return audio_duration;
    }

    public InlineQueryResultAudio setAudioDuration(Integer audioDuration) {
        this.audio_duration = audioDuration;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultAudio setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultAudio setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultAudio setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (audio_url == null || audio_url.isEmpty()) {
            throw new TelegramApiValidationException("AudioUrl parameter can't be empty", this);
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
        return "InlineQueryResultAudio{" +
                "id='" + id + '\'' +
                ", audioUrl='" + audio_url + '\'' +
                ", title='" + title + '\'' +
                ", performer='" + performer + '\'' +
                ", audioDuration=" + audio_duration +
                ", inputMessageContent=" + input_message_content +
                ", replyMarkup=" + getReplyMarkup() +
                ", caption='" + caption + '\'' +
                '}';
    }
}
