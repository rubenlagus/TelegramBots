package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents an audio file
 * @date 16 of July of 2015
 */
public class Audio implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String DURATION_FIELD = "duration";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String TITLE_FIELD = "title";
    private static final String PERFORMER_FIELD = "performer";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Unique identifier for this file
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Integer	Duration of the audio in seconds as defined by sender
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Optional. MIME type of the file as defined by sender
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title of the audio as defined by sender or by audio tags
    @JsonProperty(PERFORMER_FIELD)
    private String performer; ///< Optional. Performer of the audio as defined by sender or by audio tags

    public Audio() {
        super();
    }

    public String getFileId() {
        return fileId;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public String getTitle() {
        return title;
    }

    public String getPerformer() {
        return performer;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "fileId='" + fileId + '\'' +
                ", duration=" + duration +
                ", mimeType='" + mimeType + '\'' +
                ", fileSize=" + fileSize +
                ", title='" + title + '\'' +
                ", performer='" + performer + '\'' +
                '}';
    }
}
