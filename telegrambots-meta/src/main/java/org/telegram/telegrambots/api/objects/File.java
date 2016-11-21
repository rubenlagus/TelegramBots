package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(FILE_ID)
    private String fileId; ///< Unique identifier for this file
    @JsonProperty(FILE_SIZE_FIELD)
    private Integer fileSize; ///< Optional. File size, if known
    @JsonProperty(FILE_PATH_FIELD)
    private String filePath; ///< Optional. File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.

    public File() {
        super();
    }

    public String getFileId() {
        return fileId;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId='" + fileId + '\'' +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public String getFileUrl(String botToken) {
        return getFileUrl(botToken, filePath);
    }

    public static String getFileUrl(String botToken, String filePath) {
        if (botToken == null || botToken.isEmpty()) {
            throw new InvalidParameterException("Bot token can't be empty");
        }
        return MessageFormat.format("https://api.telegram.org/file/bot{0}/{1}", botToken, filePath);
    }
}
