package org.telegram.telegrambots.api.objects.inlinequery.result.chached;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a video animation (H.264/MPEG-4 AVC video without sound). By default,
 * this animated MPEG-4 file will be sent by the user with optional caption. Alternatively, you can
 * use input_message_content to send a message with the specified content instead of the animation.
 * @date 01 of January of 2016
 */
public class InlineQueryResultCachedMpeg4Gif implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String MPEG4_FILE_ID_FIELD = "mpeg4_file_id";
    private static final String TITLE_FIELD = "title";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";


    private final String type = "mpeg4_gif"; ///< Type of the result, must be "mpeg4_gif"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String mpeg4_file_id; ///< A valid file identifier for the MP4 file

    private String title; ///< Optional. Title for the result

    private String caption; ///< Optional. Caption of the MPEG-4 file to be sent

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the photo

    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultCachedMpeg4Gif() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedMpeg4Gif setId(String id) {
        this.id = id;
        return this;
    }

    public String getMpeg4file_id() {
        return mpeg4_file_id;
    }

    public InlineQueryResultCachedMpeg4Gif setMpeg4file_id(String mpeg4file_id) {
        this.mpeg4_file_id = mpeg4file_id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedMpeg4Gif setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedMpeg4Gif setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultCachedMpeg4Gif setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedMpeg4Gif setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (mpeg4_file_id == null || mpeg4_file_id.isEmpty()) {
            throw new TelegramApiValidationException("Mpeg4FileId parameter can't be empty", this);
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
        return "InlineQueryResultCachedMpeg4Gif{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mpeg4Url='" + mpeg4_file_id + '\'' +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
