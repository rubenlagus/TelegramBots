package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to an animated GIF file. By default, this animated GIF file will be sent
 * by the user with optional caption. Alternatively, you can use input_message_content to send a
 * message with the specified content instead of the animation.
 * @date 01 of January of 2016
 */
public class InlineQueryResultGif implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String GIFURL_FIELD = "gif_url";
    private static final String GIFWIDTH_FIELD = "gif_width";
    private static final String GIFHEIGHT_FIELD = "gif_height";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String TITLE_FIELD = "title";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";


    private final String type = "gif"; ///< Type of the result, must be "gif"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String gif_url; ///< A valid URL for the GIF file. File size must not exceed 1MB

    private Integer gif_width; ///< Optional. Width of the GIF

    private Integer gif_height; ///< Optional. Height of the GIF

    private String thumb_url; ///< Optional. URL of a static thumbnail for the result (jpeg or gif)

    private String title; ///< Optional. Title for the result

    private String caption; ///< Optional. Caption of the GIF file to be sent

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the GIF animation

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    public InlineQueryResultGif() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultGif setId(String id) {
        this.id = id;
        return this;
    }

    public String getGifUrl() {
        return gif_url;
    }

    public InlineQueryResultGif setGifUrl(String gifUrl) {
        this.gif_url = gifUrl;
        return this;
    }

    public Integer getGifWidth() {
        return gif_width;
    }

    public InlineQueryResultGif setGifWidth(Integer gifWidth) {
        this.gif_width = gifWidth;
        return this;
    }

    public Integer getGifHeight() {
        return gif_height;
    }

    public InlineQueryResultGif setGifHeight(Integer gifHeight) {
        this.gif_height = gifHeight;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultGif setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultGif setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultGif setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultGif setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultGif setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (gif_url == null || gif_url.isEmpty()) {
            throw new TelegramApiValidationException("GifUrl parameter can't be empty", this);
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
        return "InlineQueryResultGif{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", gifUrl='" + gif_url + '\'' +
                ", gifWidth=" + gif_width +
                ", gifHeight=" + gif_height +
                ", thumbUrl='" + thumb_url + '\'' +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
