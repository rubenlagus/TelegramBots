package org.telegram.telegrambots.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.api.objects.stickers.StickerSet;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get a sticker set. On success, a StickerSet object is returned.
 */
public class GetStickerSet extends BotApiMethod<StickerSet> {
    private static final String PATH = "getStickerSet";

    private static final String NAME_FIELD = "name";

    @JsonProperty(NAME_FIELD)
    private String name; ///< Name of the sticker set

    public GetStickerSet(String name) {
        super();
        this.name = checkNotNull(name);
    }

    public GetStickerSet() {
        super();
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public StickerSet deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<StickerSet> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<StickerSet>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting sticker set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name == null || name.isEmpty()) {
            throw new TelegramApiValidationException("Name can't be null", this);
        }
    }

    public String getName() {
        return name;
    }

    public GetStickerSet setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "GetStickerSet{" +
                "name='" + name + '\'' +
                '}';
    }
}
