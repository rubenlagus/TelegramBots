package org.telegram.telegrambots.meta.api.methods.send;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 3.5
 *
 * Use this method to send a group of photos, videos, documents or audios as an album.
 * Documents and audio files can be only group in an album with messages of the same type.
 * On success, an array of Messages that were sent is returned.
 */
@SuppressWarnings("unused")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SendMediaGroup extends PartialBotApiMethod<ArrayList<Message>> {
    public static final String PATH = "sendMediaGroup";

    public static final String CHATID_FIELD = "chat_id";
    public static final String MESSAGETHREADID_FIELD = "message_thread_id";
    public static final String MEDIA_FIELD = "media";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    public static final String PROTECTCONTENT_FIELD = "protect_content";
    public static final String REPLY_PARAMETERS_FIELD = "reply_parameters";

    @NonNull
    private String chatId; ///<  	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    private Integer messageThreadId;
    @NonNull
    private List<InputMedia> medias; ///< A JSON-serialized array describing photos and videos to be sent, must include 2–10 items
    private Integer replyToMessageId; ///< Optional. If the messages are a reply, ID of the original message
    private Boolean disableNotification; ///< Optional. Sends the messages silently. Users will receive a notification with no sound.
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving
    /**
     * Optional
     * Description of the message to reply to
     */
    private ReplyParameters replyParameters;

    @Tolerate
    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId.toString();
    }

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public ArrayList<Message> deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseArray(answer, Message.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (medias.isEmpty()) {
            throw new TelegramApiValidationException("Media parameter can't be empty", this);
        } else if (medias.size() < 2 || medias.size() > 10) {
            throw new TelegramApiValidationException("Number of media should be between 2 and 10", this);
        }

        for (InputMedia inputMedia : medias) {
            if (inputMedia == null) {
                throw new TelegramApiValidationException("Media parameter can not be empty", this);
            } else if (inputMedia instanceof InputMediaAnimation) {
                throw new TelegramApiValidationException("Media parameter can not be an Animation", this);
            } else {
                inputMedia.validate();
            }
        }

        if (medias.stream().anyMatch(x -> x instanceof InputMediaAudio)) {
            if (!medias.stream().allMatch(x -> x instanceof InputMediaAudio)) {
                throw new TelegramApiValidationException("Media parameter containing Audio can not have other types", this);
            }
        } else if (medias.stream().anyMatch(x -> x instanceof InputMediaDocument)) {
            if (!medias.stream().allMatch(x -> x instanceof InputMediaDocument)) {
                throw new TelegramApiValidationException("Media parameter containing Document can not have other types", this);
            }
        }

        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    public static class SendMediaGroupBuilder {

        @Tolerate
        public SendMediaGroupBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
