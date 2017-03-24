package org.telegram.telegrambots.api.objects.inlinequery.result;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to an article or web page.
 * @date 01 of January of 2016
 */
public class InlineQueryResultArticle implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String URL_FIELD = "url";
    private static final String HIDEURL_FIELD = "hide_url";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBWIDTH_FIELD = "thumb_width";
    private static final String THUMBHEIGHT_FIELD = "thumb_height";


    private final String type = "article"; ///< Type of the result, must be “article”

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String title; ///< Title of the result

    private InputMessageContent input_message_content; ///< Content of the message to be sent

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private String url; ///< Optional. URL of the result

    private Boolean hide_url; ///< Optional. Pass True, if you don't want the URL to be shown in the message

    private String description; ///< Optional. Short description of the result

    private String thumb_url; ///< Optional. Url of the thumbnail for the result

    private Integer thumb_width; ///< Optional. Thumbnail width

    private Integer thumb_height; ///< Optional. Thumbnail height

    public InlineQueryResultArticle() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultArticle setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultArticle setTitle(String title) {
        this.title = title;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultArticle setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultArticle setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public InlineQueryResultArticle setUrl(String url) {
        this.url = url;
        return this;
    }

    public Boolean getHideUrl() {
        return hide_url;
    }

    public InlineQueryResultArticle setHideUrl(Boolean hideUrl) {
        this.hide_url = hideUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultArticle setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getThumbUrl() {
        return thumb_url;
    }

    public InlineQueryResultArticle setThumbUrl(String thumbUrl) {
        this.thumb_url = thumbUrl;
        return this;
    }

    public Integer getThumbWidth() {
        return thumb_width;
    }

    public InlineQueryResultArticle setThumbWidth(Integer thumbWidth) {
        this.thumb_width = thumbWidth;
        return this;
    }

    public Integer getThumbHeight() {
        return thumb_height;
    }

    public InlineQueryResultArticle setThumbHeight(Integer thumbHeight) {
        this.thumb_height = thumbHeight;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (input_message_content == null) {
            throw new TelegramApiValidationException("InputMessageContent parameter can't be null", this);
        }
        input_message_content.validate();
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "InlineQueryResultArticle{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                ", url='" + url + '\'' +
                ", hideUrl=" + hide_url +
                ", description='" + description + '\'' +
                ", thumbUrl='" + thumb_url + '\'' +
                ", thumbWidth=" + thumb_width +
                ", thumbHeight=" + thumb_height +
                '}';
    }
}
