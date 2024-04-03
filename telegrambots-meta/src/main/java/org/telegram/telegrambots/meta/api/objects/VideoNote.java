package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents a video message (available in Telegram apps as of v.4.0).
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
public class VideoNote implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String LENGTH_FIELD = "length";
    private static final String DURATION_FIELD = "duration";
    private static final String THUMBNAIL_FIELD = "thumbnail";
    private static final String FILESIZE_FIELD = "file_size";

    /**
     * Identifier for this file, which can be used to download or reuse the file
     */
    @JsonProperty(FILEID_FIELD)
    private String fileId;
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    /**
     * Video width and height as defined by sender
     */
    @JsonProperty(LENGTH_FIELD)
    private Integer length;
    /**
     * Duration of the video in seconds as defined by sender
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
    /**
     * Optional.
     * Video thumbnail
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private PhotoSize thumbnail;
    /**
     * Optional.
     * File size
     */
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize;
}
