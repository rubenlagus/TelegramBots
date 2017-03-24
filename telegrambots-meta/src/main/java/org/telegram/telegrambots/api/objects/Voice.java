package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a voice note
 * @date 16 of July of 2015
 */
public class Voice implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String DURATION_FIELD = "duration";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";


    private String file_id; ///< Unique identifier for this file

    private Integer duration; ///< Integer	Duration of the audio in seconds as defined by sender

    private String mime_type; ///< Optional. MIME type of the file as defined by sender

    private Integer file_size; ///< Optional. File size

    public Voice() {
        super();
    }

    public String getFileId() {
        return file_id;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getMimeType() {
        return mime_type;
    }

    public Integer getFileSize() {
        return file_size;
    }

    @Override
    public String toString() {
        return "Voice{" +
                "file_id='" + file_id + '\'' +
                ", duration=" + duration +
                ", mimeType='" + mime_type + '\'' +
                ", fileSize=" + file_size +
                '}';
    }
}
