package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * This object represents a live photo.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivePhoto implements BotApiObject {
    private static final String PHOTO_FIELD = "photo";
    private static final String FILE_ID_FIELD = "file_id";
    private static final String FILE_UNIQUE_ID_FIELD = "file_unique_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String MIME_TYPE_FIELD = "mime_type";
    private static final String FILE_SIZE_FIELD = "file_size";

    /**
     * Optional. Available sizes of the corresponding static photo
     */
    @JsonProperty(PHOTO_FIELD)
    private List<PhotoSize> photo;
    /**
     * Identifier for the video file which can be used to download or reuse the file
     */
    @JsonProperty(FILE_ID_FIELD)
    private String fileId;
    /**
     * Unique identifier for the video file which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILE_UNIQUE_ID_FIELD)
    private String fileUniqueId;
    /**
     * Video width as defined by the sender
     */
    @JsonProperty(WIDTH_FIELD)
    private Integer width;
    /**
     * Video height as defined by the sender
     */
    @JsonProperty(HEIGHT_FIELD)
    private Integer height;
    /**
     * Duration of the video in seconds as defined by the sender
     */
    @JsonProperty(DURATION_FIELD)
    private Integer duration;
    /**
     * Optional. MIME type of the file as defined by the sender
     */
    @JsonProperty(MIME_TYPE_FIELD)
    private String mimeType;
    /**
     * Optional. File size in bytes.
     */
    @JsonProperty(FILE_SIZE_FIELD)
    private Long fileSize;
}
