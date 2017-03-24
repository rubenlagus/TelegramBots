package org.telegram.telegrambots.api.objects.inlinequery.result.chached;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a video file stored on the Telegram servers. By default, this video
 * file will be sent by the user with an optional caption. Alternatively, you can use
 * input_message_content to send a message with the specified content instead of the video.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedVideo implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String VIDEO_FILE_ID_FIELD = "video_file_id";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";


    private final String type = "video"; ///< Type of the result, must be "video"

    private String id; ///< Unique identifier of this result

    private String video_file_id; ///< A valid file identifier for the video file

    private String title; ///< Optional. Title for the result

    private String description; ///< Optional. Short description of the result

    private String caption; ///< Optional. Caption of the video to be sent, 0-200 characters

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the photo

    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultCachedVideo() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedVideo setId(String id) {
        this.id = id;
        return this;
    }

    public String getVide_file_id() {
        return video_file_id;
    }

    public InlineQueryResultCachedVideo setVide_file_id(String vide_file_id) {
        this.video_file_id = vide_file_id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedVideo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultCachedVideo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedVideo setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultCachedVideo setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedVideo setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (video_file_id == null || video_file_id.isEmpty()) {
            throw new TelegramApiValidationException("VideoFileId parameter can't be empty", this);
        }
        if (input_message_content != null) {
            input_message_content.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultCachedVideo{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", caption='" + caption + '\'' +
                ", videoFileId='" + video_file_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
