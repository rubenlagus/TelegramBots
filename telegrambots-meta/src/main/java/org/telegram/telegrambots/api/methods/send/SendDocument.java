package org.telegram.telegrambots.api.methods.send;


import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send general files. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendDocument extends PartialBotApiMethod<Message> {
    public static final String PATH = "senddocument";

    public static final String CHATID_FIELD = "chat_id";
    public static final String DOCUMENT_FIELD = "document";
    public static final String CAPTION_FIELD = "caption";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chat_id; ///< Unique identifier for the chat to send the message to or Username for the channel to send the message to
    private String document; ///< File file to send. file_id as String to resend a file that is already on the Telegram servers or Url to upload it
    private String caption; ///< Optional. Document caption (may also be used when resending documents by file_id), 0-200 characters
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disable_notification;
    private Integer reply_to_message_id; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard reply_markup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean is_new_document; ///< True to upload a new document, false to use a fileId
    private String document_name;
    private File new_document_file; ///< New document file
    private InputStream new_document_stream; ///< New document stream

    public SendDocument() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public SendDocument setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public SendDocument setChatId(Long chat_id) {
        Objects.requireNonNull(chat_id);
        this.chat_id = chat_id.toString();
        return this;
    }

    public String getDocument() {
        return document;
    }

    /**
     * Use this method to set the document to an document existing in Telegram system
     *
     * @param document File_id of the document to send
     * @note The file_id must have already been received or sent by your bot
     */
    public SendDocument setDocument(String document) {
        this.document = document;
        this.is_new_document = false;
        return this;
    }

    /**
     * Use this method to set the document to a new file
     *
     * @param file New document file
     */
    public SendDocument setNewDocument(File file) {
        Objects.requireNonNull(file, "documentName cannot be null!");
        this.is_new_document = true;
        this.new_document_file = file;
        return this;
    }

    public SendDocument setNewDocument(String documentName, InputStream inputStream) {
    	Objects.requireNonNull(documentName, "documentName cannot be null!");
    	Objects.requireNonNull(inputStream, "inputStream cannot be null!");
    	this.document_name = documentName;
        this.is_new_document = true;
        this.new_document_stream = inputStream;
        return this;
    }

    public boolean isNewDocument() {
        return is_new_document;
    }

    public String getDocumentName() {
        return document_name;
    }

    public File getNewDocumentFile() {
        return new_document_file;
    }

    public InputStream getNewDocumentStream() {
        return new_document_stream;
    }

    public Integer getReplyToMessageId() {
        return reply_to_message_id;
    }

    public SendDocument setReplyToMessageId(Integer replyToMessageId) {
        this.reply_to_message_id = replyToMessageId;
        return this;
    }

    public Boolean getDisableNotification() {
        return disable_notification;
    }

    public SendDocument enableNotification() {
        this.disable_notification = false;
        return this;
    }

    public SendDocument disableNotification() {
        this.disable_notification = true;
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
        return reply_markup;
    }

    public SendDocument setReplyMarkup(ReplyKeyboard replyMarkup) {
        this.reply_markup = replyMarkup;
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
        if (chat_id == null) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (is_new_document) {
            if (new_document_file == null && new_document_stream == null) {
                throw new TelegramApiValidationException("Document can't be empty", this);
            }
            if (new_document_stream != null && (document_name == null || document_name.isEmpty())) {
                throw new TelegramApiValidationException("Document name can't be empty", this);
            }
        } else if (document == null) {
            throw new TelegramApiValidationException("Document can't be empty", this);
        }

        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "SendDocument{" +
                "chatId='" + chat_id + '\'' +
                ", document='" + document + '\'' +
                ", replyToMessageId=" + reply_to_message_id +
                ", replyMarkup=" + reply_markup +
                ", isNewDocument=" + is_new_document +
                '}';
    }
}
