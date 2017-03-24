package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a video file.
 * @date 20 of June of 2015
 */
public class Video implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String THUMB_FIELD = "thumb";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";


    private String file_id; ///< Unique identifier for this file

    private Integer width; ///< Video width as defined by sender

    private Integer height; ///< Video height as defined by sender

    private Integer duration; ///< Duration of the video in seconds as defined by sender

    private PhotoSize thumb; ///< Video thumbnail

    private String mime_type; ///< Optional. Mime type of a file as defined by sender

    private Integer file_size; ///< Optional. File size

    public Video() {
        super();
    }

    public String getFileId() {
        return file_id;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getDuration() {
        return duration;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public String getMimeType() {
        return mime_type;
    }

    public Integer getFileSize() {
        return file_size;
    }

    @Override
    public String toString() {
        return "Video{" +
                "file_id='" + file_id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", duration=" + duration +
                ", thumb=" + thumb +
                ", mimeType='" + mime_type + '\'' +
                ", fileSize=" + file_size +
                '}';
    }
}
