package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents a file ready to be downloaded
 * @date 24 of June of 2015
 */
public class File implements BotApiObject {
    private static final String FILE_ID = "file_id";
    private static final String FILE_SIZE_FIELD = "file_size";
    private static final String FILE_PATH_FIELD = "file_path";


    private String file_id; ///< Unique identifier for this file

    private Integer fil_size; ///< Optional. File size, if known

    private String file_path; ///< Optional. File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.

    public File() {
        super();
    }

    public String getFileId() {
        return file_id;
    }

    public Integer getFileSize() {
        return fil_size;
    }

    public String getFilePath() {
        return file_path;
    }

    @Override
    public String toString() {
        return "File{" +
                "file_id='" + file_id + '\'' +
                ", fileSize=" + fil_size +
                ", filePath='" + file_path + '\'' +
                '}';
    }

    public String getFileUrl(String botToken) {
        return getFileUrl(botToken, file_path);
    }

    public static String getFileUrl(String botToken, String filePath) {
        if (botToken == null || botToken.isEmpty()) {
            throw new InvalidParameterException("Bot token can't be empty");
        }
        return MessageFormat.format("https://api.telegram.org/file/bot{0}/{1}", botToken, filePath);
    }
}
