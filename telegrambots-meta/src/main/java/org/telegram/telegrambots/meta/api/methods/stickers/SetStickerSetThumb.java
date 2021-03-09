package org.telegram.telegrambots.meta.api.methods.stickers;

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
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to set the thumbnail of a sticker set. Animated thumbnails can be set for animated sticker sets only.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetStickerSetThumb extends BotApiMethod<Boolean> {
    private static final String PATH = "setStickerSetThumb";

    public static final String NAME_FIELD = "name";
    public static final String USERID_FIELD = "user_id";
    public static final String THUMB_FIELD = "thumb";

    @NonNull
    private String name; ///< Sticker set name
    @NonNull
    private Long userId; ///< User identifier of the sticker set owner
    /**
     * A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px,
     * or a TGS animation with the thumbnail up to 32 kilobytes in size;
     * see https://core.telegram.org/animated_stickers#technical-requirements for animated sticker technical requirements.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a
     * String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data.
     * Animated sticker set thumbnail can't be uploaded via HTTP URL.
     */
    @NonNull
    private InputFile thumb;

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
                throw new TelegramApiRequestException("Error setting sticker thumb in set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name == null || name.isEmpty()) {
            throw new TelegramApiValidationException("name can't be null", this);
        }
        if (userId == null || userId == 0) {
            throw new TelegramApiValidationException("userId can't be null", this);
        }
        if (thumb == null) {
            throw new TelegramApiValidationException("thumb can't be null", this);
        }
        thumb.validate();
    }
}
