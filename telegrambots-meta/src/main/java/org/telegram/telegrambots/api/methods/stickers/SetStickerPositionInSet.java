package org.telegram.telegrambots.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to move a sticker in a set created by the bot to a specific position. Returns True on success.
 */
public class SetStickerPositionInSet extends BotApiMethod<Boolean> {
    private static final String PATH = "getStickerSet";

    private static final String STICKER_FIELD = "sticker";
    private static final String POSITION_FIELD = "position";

    @JsonProperty(STICKER_FIELD)
    private String sticker; ///< File identifier of the sticker
    @JsonProperty(STICKER_FIELD)
    private Integer position; ///< New sticker position in the set, zero-based

    public SetStickerPositionInSet(String sticker, Integer position) {
        this.sticker = checkNotNull(sticker);
        this.position = checkNotNull(position);
    }

    public SetStickerPositionInSet() {
        super();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting sticker position in set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker == null || sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
        if (position == null || position > 0) {
            throw new TelegramApiValidationException("position can't be null", this);
        }
    }

    public String getSticker() {
        return sticker;
    }

    public SetStickerPositionInSet setSticker(String sticker) {
        this.sticker = sticker;
        return this;
    }

    public Integer getPosition() {
        return position;
    }

    public SetStickerPositionInSet setPosition(Integer position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return "SetStickerPositionInSet{" +
                "sticker='" + sticker + '\'' +
                ", position=" + position +
                '}';
    }
}
