package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a chat photo.
 */
public class ChatPhoto implements BotApiObject {
    private static final String SMALLFILEID_FIELD = "small_file_id";
    private static final String BIGFILEID_FIELD = "big_file_id";

    @JsonProperty(SMALLFILEID_FIELD)
    private String smallFileId; ///< Unique file identifier of small (160x160) chat photo. This file_id can be used only for photo download.
    @JsonProperty(BIGFILEID_FIELD)
    private String bigFileId; ///< Unique file identifier of big (640x640) chat photo. This file_id can be used only for photo download.

    public ChatPhoto() {
        super();
    }

    public String getSmallFileId() {
        return smallFileId;
    }

    public String getBigFileId() {
        return bigFileId;
    }

    @Override
    public String toString() {
        return "ChatPhoto{" +
                "smallFileId='" + smallFileId + '\'' +
                ", bigFileId='" + bigFileId + '\'' +
                '}';
    }
}
