package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a location on a map. By default, the location will be sent by the user.
 * Alternatively, you can use input_message_content to send a message with the specified content
 * instead of the location.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineQueryResultLocation implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBNAIL_URL_FIELD = "thumbnail_url";
    private static final String THUMBNAIL_WIDTH_FIELD = "thumbnail_width";
    private static final String THUMBNAUK_HEIGHT_FIELD = "thumbnail_height";
    private static final String LIVE_PERIOD_FIELD = "live_period";
    private static final String HORIZONTALACCURACY_FIELD = "horizontal_accuracy";
    private static final String HEADING_FIELD = "heading";
    private static final String PROXIMITYALERTRADIUS_FIELD = "proximity_alert_radius";

    @JsonProperty(TYPE_FIELD)
    private final String type = "location"; ///< Type of the result, must be "location"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Location title
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Float latitude; ///< Location latitude in degrees
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Float longitude; ///< Location longitude in degrees
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent
    @JsonProperty(THUMBNAIL_URL_FIELD)
    private String thumbnailUrl; ///< Optional. URL of the thumbnail (jpeg only) for the file
    @JsonProperty(THUMBNAIL_WIDTH_FIELD)
    private Integer thumbnailWidth; ///< Optional. Thumbnail width
    @JsonProperty(THUMBNAUK_HEIGHT_FIELD)
    private Integer thumbnailHeight; ///< Optional. Thumbnail height
    /**
     * Optional. Period in seconds during which the location can be updated,
     * should be between 60 and 86400, or 0x7FFFFFFF for live locations that can be edited indefinitely.
     */
    @JsonProperty(LIVE_PERIOD_FIELD)
    private Integer livePeriod; ///< Optional. Period in seconds for which the location can be updated, should be between 60 and 86400.
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

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (livePeriod != null && (livePeriod < 60 || livePeriod > 86400) && livePeriod != 0x7FFFFFFF) {
            throw new TelegramApiValidationException("Live period parameter must be between 60 and 86400 or be 0x7FFFFFFF", this);
        }
        if (horizontalAccuracy != null && (horizontalAccuracy < 0 || horizontalAccuracy > 1500)) {
            throw new TelegramApiValidationException("Horizontal Accuracy parameter must be between 0 and 1500", this);
        }
        if (heading != null && (heading < 1 || heading > 360)) {
            throw new TelegramApiValidationException("Heading Accuracy parameter must be between 1 and 360", this);
        }
        if (proximityAlertRadius != null && (proximityAlertRadius < 1 || proximityAlertRadius > 100000)) {
            throw new TelegramApiValidationException("Approaching notification distance parameter must be between 1 and 100000", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
