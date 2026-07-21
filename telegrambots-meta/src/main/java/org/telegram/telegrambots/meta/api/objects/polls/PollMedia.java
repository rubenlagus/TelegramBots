package org.telegram.telegrambots.meta.api.objects.polls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Link;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.LivePhoto;
import org.telegram.telegrambots.meta.api.objects.Venue;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.location.Location;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * This object represents a media in a poll.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PollMedia implements BotApiObject {
    private static final String ANIMATION_FIELD = "animation";
    private static final String AUDIO_FIELD = "audio";
    private static final String DOCUMENT_FIELD = "document";
    private static final String LIVE_PHOTO_FIELD = "live_photo";
    private static final String LOCATION_FIELD = "location";
    private static final String PHOTO_FIELD = "photo";
    private static final String STICKER_FIELD = "sticker";
    private static final String VENUE_FIELD = "venue";
    private static final String VIDEO_FIELD = "video";
    private static final String LINK_FIELD = "link";

    /**
     * Optional. Media is an animation, information about the animation
     */
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation;
    /**
     * Optional. Media is an audio file, information about the file; currently, can't be received in a poll option
     */
    @JsonProperty(AUDIO_FIELD)
    private Audio audio;
    /**
     * Optional. Media is a general file, information about the file; currently, can't be received in a poll option
     */
    @JsonProperty(DOCUMENT_FIELD)
    private Document document;
    /**
     * Optional. Media is a live photo, information about the live photo
     */
    @JsonProperty(LIVE_PHOTO_FIELD)
    private LivePhoto livePhoto;
    /**
     * Optional. Media is a shared location, information about the location
     */
    @JsonProperty(LOCATION_FIELD)
    private Location location;
    /**
     * Optional. Media is a photo, available sizes of the photo
     */
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo;
    /**
     * Optional. Media is a sticker, information about the sticker; currently, for poll options only
     */
    @JsonProperty(STICKER_FIELD)
    private Sticker sticker;
    /**
     * Optional. Media is a venue, information about the venue
     */
    @JsonProperty(VENUE_FIELD)
    private Venue venue;
    /**
     * Optional. Media is a video, information about the video
     */
    @JsonProperty(VIDEO_FIELD)
    private Video video;
    /**
     * Optional. The HTTP link attached to the poll option
     */
    @JsonProperty(LINK_FIELD)
    private Link link;
}
