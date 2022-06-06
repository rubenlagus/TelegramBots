package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send general files. On success, the sent Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    public static final String DISABLECONTENTTYPEDETECTION_FIELD = "disable_content_type_detection";
    public static final String PROTECTCONTENT_FIELD = "protect_content";

    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to or Username for the channel to send the message to
    @NonNull
    private InputFile document; ///< File file to send. file_id as String to resend a file that is already on the Telegram servers or Url to upload it
    private String caption; ///< Optional. Document caption (may also be used when resending documents by file_id), 0-200 characters
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    /**
     * Optional.
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;
    @Singular
    private List<MessageEntity> captionEntities; ///< Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    private Boolean disableContentTypeDetection; ///< Optional	Disables automatic server-side content type detection for files uploaded using multipart/form-data
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseDefault(answer, "Error sending document");
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }

        if (document == null) {
            throw new TelegramApiValidationException("Document parameter can't be empty", this);
        }

        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty()) ) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }

        document.validate();

        if (thumb != null) {
            thumb.validate();
        }

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
