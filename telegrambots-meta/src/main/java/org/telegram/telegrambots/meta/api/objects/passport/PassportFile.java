package org.telegram.telegrambots.meta.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * This object represents a file uploaded to Telegram Passport.
 * Currently all Telegram Passport files are in JPEG format when decrypted and don't exceed 10MB.
 */
public class PassportFile implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String FILEDATE_FIELD = "file_date";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Identifier for this file, which can be used to download or reuse the file
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< File size
    @JsonProperty(FILEDATE_FIELD)
    private Integer fileDate; ///< Unix time when the file was uploaded

    public PassportFile() {
    }

    public PassportFile(String fileId, Integer fileSize, Integer fileDate) {
        this.fileId = fileId;
        this.fileSize = fileSize;
        this.fileDate = fileDate;
    }

    public String getFileId() {
        return fileId;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public Integer getFileDate() {
        return fileDate;
    }

    public String getFileUniqueId() {
        return fileUniqueId;
    }

    @Override
    public String toString() {
        return "PassportFile{" +
                "fileId='" + fileId + '\'' +
                ", fileSize=" + fileSize +
                ", fileDate=" + fileDate +
                ", fileUniqueId=" + fileUniqueId +
                '}';
    }
}
