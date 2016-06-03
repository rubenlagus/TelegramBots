package org.telegram.telegrambots.api.methods.send;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send general files. On success, the sent Message is returned.
 * @date 20 of June of 2015
 */
public class SendDocument {
    public static final String PATH = "senddocument";

    public static final String CHATID_FIELD = "chat_id";
    public static final String DOCUMENT_FIELD = "document";
    public static final String CAPTION_FIELD = "caption";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private String chatId; ///< Unique identifier for the chat to send the message to or Username for the channel to send the message to
    private String document; ///< File file to send. file_id as String to resend a file that is already on the Telegram servers
    private String caption; ///< Optional. Document caption (may also be used when resending documents by file_id), 0-200 characters
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewDocument; ///< True to upload a new document, false to use a fileId
    private File newDocumentFile; ///< New document file

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

    public String getDocument() {
        return document;
    }

    public SendDocument setDocument(String document) {
        this.document = document;
        this.isNewDocument = false;
        return this;
    }

    public SendDocument setNewDocument(File file) {
        this.document = file.getName();
        this.isNewDocument = true;
        this.newDocumentFile = file;
        return this;
    }

    public boolean isNewDocument() {
        return isNewDocument;
    }

    public File getNewDocumentFile() {
        return newDocumentFile;
    }

    public Integer getReplayToMessageId() {
        return replayToMessageId;
    }

    public SendDocument setReplayToMessageId(Integer replayToMessageId) {
        this.replayToMessageId = replayToMessageId;
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

    public ReplyKeyboard getReplayMarkup() {
        return replayMarkup;
    }

    public SendDocument setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replayMarkup = replayMarkup;
        return this;
    }

    @Override
    public String toString() {
        return "SendDocument{" +
                "chatId='" + chatId + '\'' +
                ", document='" + document + '\'' +
                ", replayToMessageId=" + replayToMessageId +
                ", replayMarkup=" + replayMarkup +
                ", isNewDocument=" + isNewDocument +
                '}';
    }
}
