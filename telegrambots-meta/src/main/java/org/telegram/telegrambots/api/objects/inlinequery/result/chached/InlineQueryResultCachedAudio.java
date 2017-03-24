package org.telegram.telegrambots.api.objects.inlinequery.result.chached;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to an mp3 audio file stored on the Telegram servers. By default, this
 * audio file will be sent by the user. Alternatively, you can use input_message_content to send a
 * message with the specified content instead of the audio.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedAudio implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String AUDIO_FILE_ID_FIELD = "audio_file_id";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String CAPTION_FIELD = "caption";


    private final String type = "audio"; ///< Type of the result, must be "audio"

    private String id; ///< Unique identifier of this result

    private String audio_file_id; ///< A valid file identifier for the audio file

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the audio

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private String caption; ///< Optional. Audio caption (may also be used when resending documents by file_id), 0-200 characters

    public InlineQueryResultCachedAudio() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedAudio setId(String id) {
        this.id = id;
        return this;
    }

    public String getAudi_file_id() {
        return audio_file_id;
    }

    public InlineQueryResultCachedAudio setAudi_file_id(String audi_file_id) {
        this.audio_file_id = audi_file_id;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultCachedAudio setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultCachedAudio setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedAudio setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (audio_file_id == null || audio_file_id.isEmpty()) {
            throw new TelegramApiValidationException("AudioFileId parameter can't be empty", this);
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
        return "InlineQueryResultCachedAudio{" +
                "id='" + id + '\'' +
                ", audioFileId='" + audio_file_id + '\'' +
                ", inputMessageContent=" + input_message_content +
                ", replyMarkup=" + reply_markup +
                ", caption='" + caption + '\'' +
                '}';
    }
}
