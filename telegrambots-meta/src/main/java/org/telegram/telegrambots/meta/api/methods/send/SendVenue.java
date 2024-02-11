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
 * Use this method to send information about a venue. On success, the sent Message is
 * returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SendVenue extends BotApiMethodMessage {
    public static final String PATH = "sendVenue";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGETHREADID_FIELD = "message_thread_id";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String TITLE_FIELD = "title";
    private static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUAREID_FIELD = "foursquare_id";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    private static final String REPLYMARKUP_FIELD = "reply_markup";
    private static final String FOURSQUARETYPE_FIELD = "foursquare_type";
    private static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";
    private static final String GOOGLEPLACEID_FIELD = "google_place_id";
    private static final String GOOGLEPLACETYPE_FIELD = "google_place_type";
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
    private Double latitude; ///< Latitude of venue location
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Double longitude; ///< Longitude of venue location
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Title of the venue
    @JsonProperty(ADDRESS_FIELD)
    @NonNull
    private String address; ///< Address of the venue
    @JsonProperty(DISABLENOTIFICATION_FIELD)
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    @JsonProperty(FOURSQUAREID_FIELD)
    private String foursquareId; ///< Optional. Foursquare identifier of the venue
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    @JsonProperty(REPLYMARKUP_FIELD)
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    @JsonProperty(FOURSQUARETYPE_FIELD)
    private String foursquareType; ///< Optional. Foursquare type of the venue, if known.
    @JsonProperty(ALLOWSENDINGWITHOUTREPLY_FIELD)
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
    @JsonProperty(GOOGLEPLACEID_FIELD)
    private String googlePlaceId; ///< Optional. Google Places identifier of the venue
    @JsonProperty(GOOGLEPLACETYPE_FIELD)
    private String googlePlaceType; ///< Optional. Google Places type of the venue. (See supported types.)
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
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
        if (replyParameters != null) {
            replyParameters.validate();
        }
    }

    public static class SendVenueBuilder {

        @Tolerate
        public SendVenueBuilder chatId(@NonNull Long chatId) {
            this.chatId = chatId.toString();
            return this;
        }
    }
}
