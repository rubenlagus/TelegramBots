package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one size of a photo or a file / sticker thumbnail.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSize implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String FILEPATH_FIELD = "file_path";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Identifier for this file, which can be used to download or reuse the file
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Photo width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Photo height
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size
    @JsonProperty(FILEPATH_FIELD)
    private String filePath; ///< Undocumented field. Optional. Can contain the path to download the file directly without calling to getFile
}
