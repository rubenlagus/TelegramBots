package org.telegram.telegrambots.meta.api.methods.send;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.ReplyParameters;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send point on the map. On success, the sent Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SendLocation extends BotApiMethodMessage {
    public static final String PATH = "sendlocation";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";

    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";
    private static final String LIVEPERIOD_FIELD = "live_period";
    private static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    private static final String HORIZONTALACCURACY_FIELD = "horizontal_accuracy";
    private static final String HEADING_FIELD = "heading";
    private static final String PROXIMITYALERTRADIUS_FIELD = "proximity_alert_radius";
    private static final String PROTECTCONTENT_FIELD = "protect_content";
    private static final String REPLY_PARAMETERS_FIELD = "reply_parameters";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Unique identifier for the target message thread (topic) of the forum;
     * for forum supergroups only
     */
    @JsonProperty(MESSAGETHREADID_FIELD)
    private Integer messageThreadId;
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Double latitude; ///< Latitude of location
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Double longitude; ///< Longitude of location
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    @JsonProperty(REPLYMARKUP_FIELD)
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    @JsonProperty(LIVEPERIOD_FIELD)
    private Integer livePeriod; ///< Optional. Period in seconds for which the location will be updated (see Live Locations), should be between 60 and 86400.
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    /**
     * Optional.
     * The radius of uncertainty for the location, measured in meters; 0-1500
     */
    @JsonProperty(HORIZONTALACCURACY_FIELD)
    private Double horizontalAccuracy;
    /**
     * Optional.
     * For live locations, a direction in which the user is moving, in degrees.
     * Must be between 1 and 360 if specified.
     */
    @JsonProperty(HEADING_FIELD)
    private Integer heading;
    /**
     * Optional.
     * For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters.
     * Must be between 1 and 100000 if specified.
     */
    @JsonProperty(PROXIMITYALERTRADIUS_FIELD)
    private Integer proximityAlertRadius;
    @JsonProperty(PROTECTCONTENT_FIELD)
    private Boolean protectContent; ///< Optional. Protects the contents of sent messages from forwarding and saving
    /**
     * Optional
     * Description of the message to reply to
     */
    @JsonProperty(REPLY_PARAMETERS_FIELD)
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
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (horizontalAccuracy != null && (horizontalAccuracy < 0 || horizontalAccuracy > 1500)) {
            throw new TelegramApiValidationException("Horizontal Accuracy parameter must be between 0 and 1500", this);
        }
        if (heading != null && (heading < 1 || heading > 360)) {
            throw new TelegramApiValidationException("Heading Accuracy parameter must be between 1 and 360", this);
        }
        if (proximityAlertRadius != null && (proximityAlertRadius < 1 || proximityAlertRadius > 100000)) {
            throw new TelegramApiValidationException("Proximity alert radius parameter must be between 1 and 100000", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (livePeriod != null && (livePeriod < 60 || livePeriod > 86400)) {
            throw new TelegramApiValidationException("Live period parameter must be between 60 and 86400", this);
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static class SendLocationBuilder {

        @Tolerate
        public SendLocationBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
