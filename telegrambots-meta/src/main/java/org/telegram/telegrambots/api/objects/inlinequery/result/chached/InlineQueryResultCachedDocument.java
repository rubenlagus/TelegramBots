package org.telegram.telegrambots.api.objects.inlinequery.result.chached;



import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a file stored on the Telegram servers. By default, this file will be
 * sent by the user with an optional caption. Alternatively, you can use input_message_content to
 * send a message with the specified content instead of the file.
 * @note Currently, only pdf-files and zip archives can be sent using this method.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
public class InlineQueryResultCachedDocument implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DOCUMENT_FILE_ID_FIELD = "document_file_id";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";


    private final String type = "document"; ///< Type of the result, must be "document"

    private String id; ///< Unique identifier of this result, 1-64 bytes

    private String title; ///< Optional. Title for the result

    private String document_file_id; ///< A valid file identifier for the file

    private String description; ///< Optional. Short description of the result

    private String caption; ///< Optional. Caption of the document to be sent, 0-200 characters

    private InlineKeyboardMarkup reply_markup; ///< Optional. Inline keyboard attached to the message

    private InputMessageContent input_message_content; ///< Optional. Content of the message to be sent instead of the file

    public InlineQueryResultCachedDocument() {
        super();
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedDocument setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDocumen_file_id() {
        return document_file_id;
    }

    public InlineQueryResultCachedDocument setDocumen_file_id(String documen_file_id) {
        this.document_file_id = documen_file_id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultCachedDocument setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedDocument setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public InlineQueryResultCachedDocument setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return input_message_content;
    }

    public InlineQueryResultCachedDocument setInputMessageContent(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
        return this;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (document_file_id == null || document_file_id.isEmpty()) {
            throw new TelegramApiValidationException("DocumentFileId parameter can't be empty", this);
        }
        if (title == null || title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
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
        return "InlineQueryResultCachedDocument{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", documentFileId='" + document_file_id + '\'' +
                ", caption='" + caption + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", inputMessageContent='" + input_message_content + '\'' +
                ", replyMarkup='" + reply_markup + '\'' +
                '}';
    }
}
