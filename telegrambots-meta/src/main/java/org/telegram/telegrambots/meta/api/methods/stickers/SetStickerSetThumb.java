package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to set the thumbnail of a sticker set. Animated thumbnails can be set for animated sticker sets only.
 * Returns True on success.
 */
public class SetStickerSetThumb extends BotApiMethod<Boolean> {
    private static final String PATH = "setStickerSetThumb";

    public static final String NAME_FIELD = "name";
    public static final String USERID_FIELD = "user_id";
    public static final String THUMB_FIELD = "thumb";

    private String name; ///< Sticker set name
    private Integer userId; ///< User identifier of the sticker set owner
    /**
     * A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px,
     * or a TGS animation with the thumbnail up to 32 kilobytes in size;
     * see https://core.telegram.org/animated_stickers#technical-requirements for animated sticker technical requirements.
     * Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a
     * String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data.
     * Animated sticker set thumbnail can't be uploaded via HTTP URL.
     */
    private InputFile thumb;

    public SetStickerSetThumb() {
        super();
    }

    public SetStickerSetThumb(String name, Integer userId, InputFile thumb) {
        this.name = name;
        this.userId = userId;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public InputFile getThumb() {
        return thumb;
    }

    public SetStickerSetThumb setThumb(String thumb) {
        this.thumb = new InputFile(thumb);
        return this;
    }

    public SetStickerSetThumb setThumb(InputFile thumb) {
        this.thumb = thumb;
        return this;
    }

    public SetStickerSetThumb setThumb(File thumbFile) {
        this.thumb =  new InputFile(checkNotNull(thumbFile), thumbFile.getName());;
        return this;
    }

    public SetStickerSetThumb setThumb(String thumbName, InputStream thumbStream) {
        this.thumb = new InputFile(checkNotNull(thumbStream), checkNotNull(thumbName));
        return this;
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

    @Override
    public String toString() {
        return "SetStickerSetThumb{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", thumb=" + thumb +
                '}';
    }
}
