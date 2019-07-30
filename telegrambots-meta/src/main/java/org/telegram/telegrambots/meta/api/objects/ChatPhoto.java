package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a chat photo (profile picture of a user, group or channel)
 */
public class ChatPhoto implements BotApiObject {
    private static final String SMALLFILEID_FIELD = "small_file_id";
    private static final String BIGFILEID_FIELD = "big_file_id";

    /**
     * Unique file identifier of a small chat photo (160x160).
     * This file_id can be used only for photo download and only for as long as the photo is not changed.
     */
    @JsonProperty(SMALLFILEID_FIELD)
    private String smallFileId;
    /**
     * Unique file identifier of a big chat photo (640x640).
     * This file_id can be used only for photo download and only for as long as the photo is not changed.
     */
    @JsonProperty(BIGFILEID_FIELD)
    private String bigFileId;

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
