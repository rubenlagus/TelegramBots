package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
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
public class InlineQueryResultMpeg4Gif implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String MPEG4URL_FIELD = "mpeg4_url";
    private static final String MPEG4WIDTH_FIELD = "mpeg4_width";
    private static final String MPEG4HEIGHT_FIELD = "mpeg4_height";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String TITLE_FIELD = "title";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";


    private final String type = "mpeg4_gif"; ///< Type of the result, must be "mpeg4_gif"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String mpeg4_url; ///< A valid URL for the MP4 file. File size must not exceed 1MB

    private Integer mpeg4_width; ///< Optional. Video width

    private Integer mpeg4_height; ///< Optional. Video height

    private String thumb_url; ///< Optional. URL of the static thumbnail (jpeg or gif) for the result

    private String title; ///< Optional. Title for the result

    private String caption; ///< Optional. Caption of the MPEG-4 file to be sent

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the photo

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultMpeg4Gif() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultMpeg4Gif setId(String id) {
        this.id = id;
        return this;
    }

    public String getMpeg4Url() {
        return mpeg4_url;
    }

    public InlineQueryResultMpeg4Gif setMpeg4Url(String mpeg4Url) {
        this.mpeg4_url = mpeg4Url;
        return this;
    }

    public Integer getMpeg4Width() {
        return mpeg4_width;
    }

    public InlineQueryResultMpeg4Gif setMpeg4Width(Integer mpeg4Width) {
        this.mpeg4_width = mpeg4Width;
        return this;
    }

    public Integer getMpeg4Height() {
        return mpeg4_height;
    }

    public InlineQueryResultMpeg4Gif setMpeg4Height(Integer mpeg4Height) {
        this.mpeg4_height = mpeg4Height;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultMpeg4Gif setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultMpeg4Gif setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultMpeg4Gif setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultMpeg4Gif setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultMpeg4Gif setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (mpeg4_url == null || mpeg4_url.isEmpty()) {
            throw new TelegramApiValidationException("Mpeg4Url parameter can't be empty", this);
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
        return "InlineQueryResultMpeg4Gif{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", mpeg4Url='" + mpeg4_url + '\'' +
                ", mpeg4Width=" + mpeg4_width +
                ", mpeg4Height=" + mpeg4_height +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
