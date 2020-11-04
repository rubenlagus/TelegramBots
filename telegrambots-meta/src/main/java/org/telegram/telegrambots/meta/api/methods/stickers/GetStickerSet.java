package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.stickers.StickerSet;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get a sticker set. On success, a StickerSet object is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStickerSet extends BotApiMethod<StickerSet> {
    private static final String PATH = "getStickerSet";

    private static final String NAME_FIELD = "name";

    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name; ///< Name of the sticker set

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
}
