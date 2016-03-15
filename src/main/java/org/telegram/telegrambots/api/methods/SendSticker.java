package org.telegram.telegrambots.api.methods;

import org.telegram.telegrambots.api.objects.ReplyKeyboard;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 20 of June of 2015
 */
public class SendSticker {
    public static final String PATH = "sendsticker";

    public static final String CHATID_FIELD = "chat_id";
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    public static final String STICKER_FIELD = "sticker";
    private String sticker; ///< Sticker file to send. file_id as String to resend a sticker that is already on the Telegram servers
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    /**
     * Optional. Sends the message silently.
     * iOS users will not receive a notification,
     * Android users will receive a notification with no sound.
     * Other apps coming soon
     */
    private Boolean disableNotification;
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard

    private boolean isNewSticker;
    private String stickerName;

    public SendSticker() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSticker() {
        return sticker;
    }

    public Integer getReplayToMessageId() {
        return replayToMessageId;
    }

    public void setReplayToMessageId(Integer replayToMessageId) {
        this.replayToMessageId = replayToMessageId;
    }

    public ReplyKeyboard getReplayMarkup() {
        return replayMarkup;
    }

    public void setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replayMarkup = replayMarkup;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
        this.isNewSticker = false;
    }

    public void setSticker(String sticker, String stickerName) {
        this.sticker = sticker;
        this.isNewSticker = true;
        this.stickerName = stickerName;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    public boolean isNewSticker() {
        return isNewSticker;
    }

    public String getStickerName() {
        return stickerName;
    }
}
