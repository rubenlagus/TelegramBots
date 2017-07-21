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
 * Use this method to delete a sticker from a set created by the bot. Returns True on success.
 */
public class DeleteStickerFromSet extends BotApiMethod<Boolean> {
    private static final String PATH = "deleteStickerFromSet";

    private static final String STICKER_FIELD = "sticker";

    @JsonProperty(STICKER_FIELD)
    private String sticker; ///< File identifier of the sticker

    public DeleteStickerFromSet() {
        super();
    }

    public DeleteStickerFromSet(String sticker) {
        super();
        this.sticker = checkNotNull(sticker);
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
                throw new TelegramApiRequestException("Error deleting sticker from set", result);
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
    }

    public String getSticker() {
        return sticker;
    }

    public DeleteStickerFromSet setSticker(String sticker) {
        this.sticker = sticker;
        return this;
    }

    @Override
    public String toString() {
        return "DeleteStickerFromSet{" +
                "sticker='" + sticker + '\'' +
                '}';
    }
}
