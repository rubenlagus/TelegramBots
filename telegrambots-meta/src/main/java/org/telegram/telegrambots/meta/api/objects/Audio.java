package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * This object represents an audio file
 * @author Ruben Bermudez
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Audio implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String DURATION_FIELD = "duration";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String TITLE_FIELD = "title";
    private static final String PERFORMER_FIELD = "performer";
    private static final String THUMBNAIL_FIELD = "thumbnail";
    private static final String FILENAME_FIELD = "file_name";

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
     * Integer Duration of the audio in seconds as defined by sender
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
    /**
     * Optional.
     * MIME type of the file as defined by sender
     */
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType;
    /**
     * Optional.
     * File size in bytes.
     * It can be bigger than 2^31 and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing this value.
     */
    @JsonProperty(FILESIZE_FIELD)
    private Long fileSize;
    /**
     * Optional.
     * Title of the audio as defined by sender or by audio tags
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * Optional.
     * Performer of the audio as defined by sender or by audio tags
     */
    @JsonProperty(PERFORMER_FIELD)
    private String performer;
    /**
     * Optional.
     * Thumbnail of the album cover to which the music file belongs
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private PhotoSize thumbnail;
    /**
     * Optional.
     * Original filename as defined by sender
     */
    @JsonProperty(FILENAME_FIELD)
    private String fileName;

    /**
     * @deprecated Use {{@link #getThumbnail()}}
     */
    @JsonIgnore
    @Deprecated
    public PhotoSize getThumb() {
        return thumbnail;
    }

    /**
     * @deprecated Use {{@link #setThumbnail(PhotoSize)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumb(PhotoSize thumb) {
        this.thumbnail = thumb;
    }
}
