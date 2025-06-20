/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.telegram.telegrambots.meta.api.objects.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;

/**
 * This object represents an animation file (GIF or H.264/MPEG-4 AVC video without sound).
 *
 * @author Ruben Bermudez
 * @version 2.4
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
public class Animation implements BotApiObject {
    private static final String FILE_ID_FIELD = "file_id";
    private static final String FILE_UNIQUE_ID_FIELD = "file_unique_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String THUMBNAIL_FIELD = "thumbnail";
    private static final String FILE_NAME_FIELD = "file_name";
    private static final String MIME_TYPE_FIELD = "mime_type";
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
     * Video width as defined by sender
     */
    @JsonProperty(WIDTH_FIELD)
    @NonNull
    private Integer width;
    /**
     * Video height as defined by sender
     */
    @JsonProperty(HEIGHT_FIELD)
    @NonNull
    private Integer height;
    /**
     * Duration of the video in seconds as defined by sender
     */
    @JsonProperty(DURATION_FIELD)
    @NonNull
    private Integer duration;
    /**
     * Optional.
     * Animation thumbnail as defined by sender
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private PhotoSize thumbnail;
    /**
     * Optional.
     * Original animation filename as defined by sender
     */
    @JsonProperty(FILE_NAME_FIELD)
    private String fileName;
    /**
     * Optional.
     * MIME type of the file as defined by sender
     */
    @JsonProperty(MIME_TYPE_FIELD)
    private String mimeType;
    /**
     * Optional.
     * File size in bytes.
     * It can be bigger than 2^31 and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing this value.
     */
    @JsonProperty(FILE_SIZE_FIELD)
    private Long fileSize;
}
