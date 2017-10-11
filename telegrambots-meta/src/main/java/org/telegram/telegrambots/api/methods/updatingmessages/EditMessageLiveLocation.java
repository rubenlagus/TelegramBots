package org.telegram.telegrambots.api.methods.updatingmessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to edit live location messages sent by the bot or via the bot (for inline bots).
 * A location can be edited until its live_period expires or editing is explicitly disabled by a call to
 * stopMessageLiveLocation. On success, if the edited message was sent by the bot, the edited Message is returned,
 * otherwise True is returned.
 */
public class EditMessageLiveLocation extends BotApiMethod<Serializable> {
    public static final String PATH = "editMessageLiveLocation";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String INLINE_MESSAGE_ID_FIELD = "inline_message_id";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String REPLYMARKUP_FIELD = "reply_markup";

    /**
     * Required if inline_message_id is not specified. Unique identifier for the chat to send the
     * message to (Or username for channels)
     */
    @JsonProperty(CHATID_FIELD)
    private String chatId;
    /**
     * Required if inline_message_id is not specified. Unique identifier of the sent message
     */
    @JsonProperty(MESSAGEID_FIELD)
    private Integer messageId;
    /**
     * Required if chat_id and message_id are not specified. Identifier of the inline message
     */
    @JsonProperty(INLINE_MESSAGE_ID_FIELD)
    private String inlineMessageId;
    @JsonProperty(LATITUDE_FIELD)
    private Float latitude; ///< Latitude of new location
    @JsonProperty(LONGITUDE_FIELD)
    private Float longitud; ///< Longitude of new location
    @JsonProperty(REPLYMARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. A JSON-serialized object for an inline keyboard.

    public EditMessageLiveLocation() {
        super();
    }

    public String getChatId() {
        return chatId;
    }

    public EditMessageLiveLocation setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public EditMessageLiveLocation setChatId(Long chatId) {
        this.chatId = chatId.toString();
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public EditMessageLiveLocation setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public EditMessageLiveLocation setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public EditMessageLiveLocation setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public Float getLatitude() {
        return latitude;
    }

    public EditMessageLiveLocation setLatitude(Float latitude) {
        Objects.requireNonNull(chatId);
        this.latitude = latitude;
        return this;
    }

    public Float getLongitud() {
        return longitud;
    }

    public EditMessageLiveLocation setLongitud(Float longitud) {
        Objects.requireNonNull(chatId);
        this.longitud = longitud;
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
                throw new TelegramApiRequestException("Error editing message live location", result);
            }
        } catch (IOException e) {
            try {
                ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                        new TypeReference<ApiResponse<Boolean>>() {
                        });
                if (result.getOk()) {
                    return result.getResult();
                } else {
                    throw new TelegramApiRequestException("Error editing message live location", result);
                }
            } catch (IOException e2) {
                throw new TelegramApiRequestException("Unable to deserialize response", e);
            }
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (inlineMessageId == null) {
            if (chatId == null) {
                throw new TelegramApiValidationException("ChatId parameter can't be empty if inlineMessageId is not present", this);
            }
            if (messageId == null) {
                throw new TelegramApiValidationException("MessageId parameter can't be empty if inlineMessageId is not present", this);
            }
        } else {
            if (chatId != null) {
                throw new TelegramApiValidationException("ChatId parameter must be empty if inlineMessageId is provided", this);
            }
            if (messageId != null) {
                throw new TelegramApiValidationException("MessageId parameter must be empty if inlineMessageId is provided", this);
            }
        }
        if (latitude == null) {
            throw new TelegramApiValidationException("Latitude parameter can't be empty", this);
        }
        if (longitud == null) {
            throw new TelegramApiValidationException("Longitud parameter can't be empty", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }

    @Override
    public String toString() {
        return "EditMessageLiveLocation{" +
                "chatId='" + chatId + '\'' +
                ", messageId=" + messageId +
                ", inlineMessageId='" + inlineMessageId + '\'' +
                ", latitude=" + latitude +
                ", longitud=" + longitud +
                ", replyMarkup=" + replyMarkup +
                '}';
    }
}
