package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.4
 *
 * This object represents a video file of a specific quality.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoQuality implements BotApiObject {
    private static final String FILE_ID_FIELD = "file_id";
    private static final String FILE_UNIQUE_ID_FIELD = "file_unique_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String CODEC_FIELD = "codec";
    private static final String FILE_SIZE_FIELD = "file_size";

    /**
     * Identifier for this file, which can be used to download or reuse the file
     */
    @JsonProperty(FILE_ID_FIELD)
    @NonNull
    private String fileId;
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILE_UNIQUE_ID_FIELD)
    @NonNull
    private String fileUniqueId;
    /**
     * Video width
     */
    @JsonProperty(WIDTH_FIELD)
    @NonNull
    private Integer width;
    /**
     * Video height
     */
    @JsonProperty(HEIGHT_FIELD)
    @NonNull
    private Integer height;
    /**
     * Codec that was used to encode the video, for example, "h264", "h265", or "av01"
     */
    @JsonProperty(CODEC_FIELD)
    @NonNull
    private String codec;
    /**
     * Optional.
     * File size in bytes.
     * It can be bigger than 2^31 and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing this value.
     */
    @JsonProperty(FILE_SIZE_FIELD)
    private Long fileSize;
}
