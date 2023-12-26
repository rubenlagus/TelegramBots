package org.telegram.telegrambots.meta.api.objects.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a venue. By default, the venue will be sent by the user. Alternatively, you can
 * use input_message_content to send a message with the specified content instead of the venue.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class InlineQueryResultVenue implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String ADDRESS_FIELD = "address";
    private static final String FOURSQUAREID_FIELD = "foursquare_id";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String THUMBNAIL_URL_FIELD = "thumbnail_url";
    private static final String THUMBNAIL_WIDTH_FIELD = "thumbnail_width";
    private static final String THUMBNAUK_HEIGHT_FIELD = "thumbnail_height";
    private static final String FOURSQUARETYPE_FIELD = "foursquare_type";
    private static final String GOOGLEPLACEID_FIELD = "google_place_id";
    private static final String GOOGLEPLACETYPE_FIELD = "google_place_type";

    @JsonProperty(TYPE_FIELD)
    private final String type = "venue"; ///< Type of the result, must be "venue"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    @NonNull
    private String title; ///< Location title
    @JsonProperty(LATITUDE_FIELD)
    @NonNull
    private Float latitude; ///< Venue latitude in degrees
    @JsonProperty(LONGITUDE_FIELD)
    @NonNull
    private Float longitude; ///< Venue longitude in degrees
    @JsonProperty(ADDRESS_FIELD)
    @NonNull
    private String address; ///< Address of the venue
    @JsonProperty(FOURSQUAREID_FIELD)
    private String foursquareId; ///< Optional. Foursquare identifier of the venue if known
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
    @JsonProperty(FOURSQUARETYPE_FIELD)
    private String foursquareType; ///< Optional. Foursquare type of the venue, if known.
    @JsonProperty(GOOGLEPLACEID_FIELD)
    private String googlePlaceId; ///< Optional. Google Places identifier of the venue
    @JsonProperty(GOOGLEPLACETYPE_FIELD)
    private String googlePlaceType; ///< Optional. Google Places type of the venue. (See supported types.)

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (title.isEmpty()) {
            throw new TelegramApiValidationException("Title parameter can't be empty", this);
        }
        if (address.isEmpty()) {
            throw new TelegramApiValidationException("Address parameter can't be empty", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }

    }

    /**
     * @deprecated Use {{@link #getThumbnailUrl()}}
     */
    @JsonIgnore
    @Deprecated
    public String getThumbUrl() {
        return thumbnailUrl;
    }

    /**
     * @deprecated Use {{@link #setThumbnailUrl(String)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumbUrl(String thumbUrl) {
        this.thumbnailUrl = thumbUrl;
    }

    /**
     * @deprecated Use {{@link #getThumbnailWidth()}}
     */
    @JsonIgnore
    @Deprecated
    public Integer getThumbWidth() {
        return thumbnailWidth;
    }

    /**
     * @deprecated Use {{@link #setThumbnailWidth(Integer)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumbWidth(Integer thumbWidth) {
        this.thumbnailWidth = thumbWidth;
    }

    /**
     * @deprecated Use {{@link #getThumbnailHeight()}}
     */
    @JsonIgnore
    @Deprecated
    public Integer getThumbHeight() {
        return thumbnailHeight;
    }

    /**
     * @deprecated Use {{@link #setThumbnailHeight(Integer)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumbHeight(Integer thumbHeight) {
        this.thumbnailHeight = thumbHeight;
    }

    public static class InlineQueryResultVenueBuilder {

        @Tolerate
        @Deprecated
        public InlineQueryResultVenueBuilder thumbUrl(String thumbUrl) {
            this.thumbnailUrl = thumbUrl;
            return this;
        }

        @Tolerate
        @Deprecated
        public InlineQueryResultVenueBuilder thumbHeight(Integer thumbHeight) {
            this.thumbnailHeight = thumbHeight;
            return this;
        }

        @Tolerate
        @Deprecated
        public InlineQueryResultVenueBuilder thumbWidth(Integer thumbWidth) {
            this.thumbnailWidth = thumbWidth;
            return this;
        }
    }
}
