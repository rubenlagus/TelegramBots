package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one size of a photo or a file / sticker thumbnail.
 * @date 20 of June of 2015
 */
public class PhotoSize implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String FILEPATH_FIELD = "file_path";


    private String file_id; ///< Unique identifier for this file

    private Integer width; ///< Photo width

    private Integer height; ///< Photo height

    private Integer file_size; ///< Optional. File size

    private String file_path; ///< Undocumented field. Optional. Can contain the path to download the file direclty without calling to getFile

    public PhotoSize() {
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

    public Integer getFileSize() {
        return file_size;
    }

    public String getFilePath() {
        return file_path;
    }

    public boolean hasFilePath() {
        return file_path != null && !file_path.isEmpty();
    }

    @Override
    public String toString() {
        return "PhotoSize{" +
                "file_id='" + file_id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", fileSize=" + file_size +
                '}';
    }
}
