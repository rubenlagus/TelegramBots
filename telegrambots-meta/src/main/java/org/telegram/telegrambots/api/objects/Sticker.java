package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a sticker.
 * @date 20 of June of 2015
 */
public class Sticker implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String THUMB_FIELD = "thumb";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String EMOJI_FIELD = "emoji";


    private String file_id; ///< Unique identifier for this file

    private Integer width; ///< Sticker width

    private Integer height; ///< Sticker height

    private PhotoSize thumb; ///< Optional. Sticker thumbnail in .webp or .jpg format

    private Integer file_size; ///< Optional. File size

    private String emoji; ///< Optional. Emoji associated with the sticker

    public Sticker() {
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

    public PhotoSize getThumb() {
        return thumb;
    }

    public Integer getFileSize() {
        return file_size;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "file_id='" + file_id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", thumb=" + thumb +
                ", fileSize=" + file_size +
                ", emoji=" + emoji +
                '}';
    }
}
