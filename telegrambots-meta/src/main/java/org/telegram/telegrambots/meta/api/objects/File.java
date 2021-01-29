package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a file ready to be downloaded
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class File implements BotApiObject {
    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String FILE_SIZE_FIELD = "file_size";
    private static final String FILE_PATH_FIELD = "file_path";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Identifier for this file, which can be used to download or reuse the file
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    @JsonProperty(FILE_SIZE_FIELD)
    private Integer fileSize; ///< Optional. File size, if known
    @JsonProperty(FILE_PATH_FIELD)
    private String filePath; ///< Optional. File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.

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
