package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a general file (as opposed to photos and audio files).
 * Telegram users can send files of any type of up to 1.5 GB in size.
 * @date 20 of June of 2015
 */
public class Document implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILENAME_FIELD = "file_name";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";


    private String file_id; ///< Unique identifier for this file

    private PhotoSize thumb; ///< Document thumbnail as defined by sender

    private String file_name; ///< Optional. Original filename as defined by sender

    private String mime_type; ///< Optional. Mime type of a file as defined by sender

    private Integer file_size; ///< Optional. File size

    public Document() {
        super();
    }

    public String getFileId() {
        return file_id;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public String getFileName() {
        return file_name;
    }

    public String getMimeType() {
        return mime_type;
    }

    public Integer getFileSize() {
        return file_size;
    }

    @Override
    public String toString() {
        return "Document{" +
                "file_id='" + file_id + '\'' +
                ", thumb=" + thumb +
                ", fileName='" + file_name + '\'' +
                ", mimeType='" + mime_type + '\'' +
                ", fileSize=" + file_size +
                '}';
    }
}
