package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send general files. On success, the sent Message is returned.
 */
public class SendDocument extends PartialBotApiMethod<Message> {
    public static final String PATH = "senddocument";

    public static final String CHATID_FIELD = "chat_id";
    public static final String DOCUMENT_FIELD = "document";
    public static final String CAPTION_FIELD = "caption";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String THUMB_FIELD = "thumb";

    private String chatId; ///< Unique identifier for the chat to send the message to or Username for the channel to send the message to
    private InputFile document; ///< File file to send. file_id as String to resend a file that is already on the Telegram servers or Url to upload it
    private String caption; ///< Optional. Document caption (may also be used when resending documents by file_id), 0-200 characters
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

    public SendDocument() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public SendDocument setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendDocument setChatId(Long chatId) {
        Objects.requireNonNull(chatId);
        this.chatId = chatId.toString();
        return this;
    }

    public InputFile getDocument() {
        return document;
    }

    /**
     * Use this method to set the document to an document existing in Telegram system
     *
     * @param document File_id of the document to send
     * @note The file_id must have already been received or sent by your bot
     */
    public SendDocument setDocument(String document) {
        this.document = new InputFile(document);
        return this;
    }

    /**
     * Use this method to set the document to a new file
     *
     * @param file New document file
     */
    public SendDocument setDocument(File file) {
        Objects.requireNonNull(file, "documentName cannot be null!");
        this.document = new InputFile(file, file.getName());
        return this;
    }

    public SendDocument setDocument(InputFile document) {
        Objects.requireNonNull(document, "document cannot be null!");
        this.document = document;
        return this;
    }

    public SendDocument setDocument(String documentName, InputStream inputStream) {
    	Objects.requireNonNull(documentName, "documentName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.document = new InputFile(inputStream, documentName);
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendDocument setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendDocument enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendDocument disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public SendDocument setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public ReplyKeyboard getReplyMarkup() {
        return replyMarkup;
    }

    public SendDocument setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public String getParseMode() {
        return parseMode;
    }

    public SendDocument setParseMode(String parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public SendDocument setThumb(InputFile thumb) {
        this.thumb = thumb;
        return this;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending document", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (document == null) {
            throw new TelegramApiValidationException("Document parameter can't be empty", this);
        }

        document.validate();

        if (thumb != null) {
            thumb.validate();
        }

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendDocument{" +
                "chatId='" + chatId + '\'' +
                ", document=" + document +
                ", caption='" + caption + '\'' +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                ", replyMarkup=" + replyMarkup +
                ", parseMode='" + parseMode + '\'' +
                ", thumb=" + thumb +
                '}';
    }
}
