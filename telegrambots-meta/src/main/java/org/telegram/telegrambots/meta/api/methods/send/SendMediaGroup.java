package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Use this method to send a group of photos or videos as an album.
 * On success, an array of the sent Messages is returned.
 */
@SuppressWarnings("unused")
public class SendMediaGroup extends PartialBotApiMethod<ArrayList<Message>> {
    public static final String PATH = "sendMediaGroup";

    public static final String CHATID_FIELD = "chat_id";
    public static final String MEDIA_FIELD = "media";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";

    @JsonProperty(CHATID_FIELD)
    private String chatId; ///<  	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(MEDIA_FIELD)
    private List<InputMedia> media; ///< A JSON-serialized array describing photos and videos to be sent
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the messages are a reply, ID of the original message
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the messages silently. Users will receive a notification with no sound.

    public SendMediaGroup() {
        super();
    }

    public SendMediaGroup(String chatId, List<InputMedia> media) {
        super();
        this.chatId = checkNotNull(chatId);
        this.media = checkNotNull(media);
    }

    public SendMediaGroup(Long chatId, List<InputMedia> media) {
        super();
        this.chatId = checkNotNull(chatId).toString();
        this.media = checkNotNull(media);
    }

    public String getChatId() {
        return chatId;
    }

    public SendMediaGroup setChatId(String chatId) {
        this.chatId = checkNotNull(chatId);
        return this;
    }

    public SendMediaGroup setChatId(Long chatId) {
        this.chatId = checkNotNull(chatId).toString();
        return this;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public SendMediaGroup setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
        return this;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendMediaGroup enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendMediaGroup disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public List<InputMedia> getMedia() {
        return media;
    }

    public void setMedia(List<InputMedia> media) {
        this.media = media;
    }

    @Override
    public ArrayList<Message> deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<ArrayList<Message>> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ArrayList<Message>>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending media group", result);
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

        if (media == null || media.isEmpty()) {
            throw new TelegramApiValidationException("Media parameter can't be empty", this);
        }

        for (InputMedia inputMedia : media) {
            inputMedia.validate();
        }
    }

    @Override
    public String toString() {
        return "SendMediaGroup{" +
                "chatId='" + chatId + '\'' +
                ", media=" + media +
                ", replyToMessageId=" + replyToMessageId +
                ", disableNotification=" + disableNotification +
                '}';
    }
}
