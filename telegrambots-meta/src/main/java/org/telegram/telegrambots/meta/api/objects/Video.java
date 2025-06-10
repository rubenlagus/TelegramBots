package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;

import java.util.List;

/**
 * This object represents a video file.
 * @author Ruben Bermudez
 * @version 1.0
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video implements BotApiObject {

    private static final String FILE_ID_FIELD = "file_id";
    private static final String FILE_UNIQUE_ID_FIELD = "file_unique_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String THUMBNAIL_FIELD = "thumbnail";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILE_SIZE_FIELD = "file_size";
    private static final String FILE_NAME_FIELD = "file_name";
    private static final String COVER_FIELD = "cover";
    private static final String START_TIMESTAMP_FIELD = "start_timestamp";

    /**
     * Identifier for this file, which can be used to download or reuse the file
     */
    @JsonProperty(FILE_ID_FIELD)
    private String fileId;
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILE_UNIQUE_ID_FIELD)
    private String fileUniqueId;
    /**
     * Video width as defined by sender
     */
    @JsonProperty(WIDTH_FIELD)
    private Integer width;
    /**
     * Video height as defined by sender
     */
    @JsonProperty(HEIGHT_FIELD)
    private Integer height;
    /**
     * Duration of the video in seconds as defined by sender
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
    /**
     * Video thumbnail
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private PhotoSize thumbnail;
    /**
     * Optional.
     * Mime type of file as defined by sender
     */
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType;
    /**
     * Optional.
     * File size in bytes.
     * It can be bigger than 2^31 and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing this value.
     */
    @JsonProperty(FILE_SIZE_FIELD)
    private Long fileSize;
    /**
     * Optional.
     * Original filename as defined by sender
     */
    @JsonProperty(FILE_NAME_FIELD)
    private String fileName;
    /**
     * Optional.
     * Available sizes of the cover of the video in the message
     */
    @JsonProperty(COVER_FIELD)
    private List<PhotoSize> cover;
    /**
     * Optional.
     * Timestamp in seconds from which the video will play in the message
     */
    @JsonProperty(START_TIMESTAMP_FIELD)
    private Integer startTimestamp;
}
