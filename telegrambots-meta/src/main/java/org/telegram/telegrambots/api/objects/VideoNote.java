package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 *
 * This object represents a video message (available in Telegram apps as of v.4.0).
 */
public class VideoNote implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String LENGTH_FIELD = "length";
    private static final String DURATION_FIELD = "duration";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILESIZE_FIELD = "file_size";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Unique identifier for this file
    @JsonProperty(LENGTH_FIELD)
    private Integer length; ///< Video width and height as defined by sender
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Duration of the video in seconds as defined by sender
    @JsonProperty(THUMB_FIELD)
    private PhotoSize thumb; ///< Optional. Video thumbnail
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size

    public VideoNote() {
        super();
    }

    public String getFileId() {
        return fileId;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getDuration() {
        return duration;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return "VideoNote{" +
                "fileId='" + fileId + '\'' +
                ", length=" + length +
                ", duration=" + duration +
                ", thumb=" + thumb +
                ", fileSize=" + fileSize +
                '}';
    }
}
