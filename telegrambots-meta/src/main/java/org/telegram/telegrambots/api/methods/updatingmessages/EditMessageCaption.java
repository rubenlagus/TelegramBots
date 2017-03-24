package org.telegram.telegrambots.api.methods.updatingmessages;




import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.myclasses.TypeReference;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to edit captions of messages sent by the bot or via the bot (for inline
 * bots). On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
 * @date 10 of April of 2016
 */
public class EditMessageCaption extends BotApiMethod<Serializable> {
    public static final String PATH = "editmessagecaption";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String CAPTION_FIELD = "caption";
    private static final String REPLYMARKUP_FIELD = "reply_markup";

    /**
     * Required if inline_message_id is not specified. Unique identifier for the chat to send the
     * message to (Or username for channels)
     */

    private String chat_id;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */

    private Integer message_id;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */

    private String inline_message_id;

    private String caption; ///< Optional. New caption of the message

    private InlineKeyboardMarkup reply_markup; ///< Optional. A JSON-serialized object for an inline keyboard.

    public EditMessageCaption() {
        super();
    }

    public String getChatId() {
        return chat_id;
    }

    public EditMessageCaption setChatId(String chat_id) {
        this.chat_id = chat_id;
        return this;
    }

    public Integer getMessageId() {
        return message_id;
    }

    public EditMessageCaption setMessageId(Integer messageId) {
        this.message_id = messageId;
        return this;
    }

    public String getInlineMessageId() {
        return inline_message_id;
    }

    public EditMessageCaption setInlineMessageId(String inlineMessageId) {
        this.inline_message_id = inlineMessageId;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public EditMessageCaption setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return reply_markup;
    }

    public EditMessageCaption setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.reply_markup = replyMarkup;
        return this;
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error editing message caption", result);
            }
        } catch (IOException e) {
            try {
                ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                        new TypeReference<ApiResponse<Boolean>>() {
                        });
                if (result.getOk()) {
                    return result.getResult();
                } else {
                    throw new TelegramApiRequestException("Error editing message caption", result);
                }
            } catch (IOException e2) {
                throw new TelegramApiRequestException("Unable to deserialize response", e);
            }
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inline_message_id == null) {
            if (chat_id == null) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (message_id == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chat_id != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (message_id != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
        if (reply_markup != null) {
            reply_markup.validate();
        }
    }

    @Override
    public String toString() {
        return "EditMessageCaption{" +
                "chatId=" + chat_id +
                ", messageId=" + message_id +
                ", inlineMessageId=" + inline_message_id +
                ", caption=" + caption +
                ", replyMarkup=" + reply_markup +
                '}';
    }
}
