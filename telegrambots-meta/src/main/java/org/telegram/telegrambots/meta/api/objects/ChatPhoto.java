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
 * This object represents a chat photo (profile picture of a user, group or channel)
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatPhoto implements BotApiObject {
    private static final String SMALLFILEID_FIELD = "small_file_id";
    private static final String SMALLFILEUNIQUEID_FIELD = "small_file_unique_id";
    private static final String BIGFILEID_FIELD = "big_file_id";
    private static final String BIGFILEUNIQUEID_FIELD = "big_file_unique_id";

    /**
     * File identifier of small (160x160) chat photo.
     * This file_id can be used only for photo download and only for as long as the photo is not changed.
     */
    @JsonProperty(SMALLFILEID_FIELD)
    private String smallFileId;
    /**
     * Unique file identifier of small (160x160) chat photo,
     * which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(SMALLFILEUNIQUEID_FIELD)
    private String smallFileUniqueId;
    /**
     * File identifier of big (640x640) chat photo.
     * This file_id can be used only for photo download and only for as long as the photo is not changed.
     */
    @JsonProperty(BIGFILEID_FIELD)
    private String bigFileId;
    /**
     * Unique file identifier of big (640x640) chat photo,
     * which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(BIGFILEUNIQUEID_FIELD)
    private String bigFileUniqueId;
}
