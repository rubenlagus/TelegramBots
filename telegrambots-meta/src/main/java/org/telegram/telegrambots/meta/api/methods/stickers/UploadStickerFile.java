package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to upload a .png file with a sticker for later use in createNewStickerSet and addStickerToSet
 * methods (can be used multiple times). Returns the uploaded File on success.
 */
public class UploadStickerFile extends PartialBotApiMethod<File> {
    public static final String PATH = "uploadStickerFile";

    public static final String USERID_FIELD = "user_id";
    public static final String PNGSTICKER_FIELD = "png_sticker";

    private Integer userId; ///< User identifier of sticker file owner
    /**
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px. More info on Sending Files »
     */
    private InputFile pngSticker; ///< New sticker file

    public UploadStickerFile() {
        super();
    }

    public UploadStickerFile(Integer userId) {
        super();
        this.userId = checkNotNull(userId);
    }

    @Override
    public File deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<File> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<File>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error uploading sticker set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null || userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }

        if (pngSticker == null) {
            throw new TelegramApiValidationException("PngSticker parameter can't be empty", this);
        }

        pngSticker.validate();
    }

    public Integer getUserId() {
        return userId;
    }

    public UploadStickerFile setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public InputFile getPngSticker() {
        return pngSticker;
    }

    public UploadStickerFile setPngSticker(java.io.File pngSticker) {
        this.pngSticker = new InputFile(pngSticker, pngSticker.getName());
        return this;
    }

    public UploadStickerFile setPngSticker(String pngStickerName, InputStream pngStickerStream) {
        this.pngSticker = new InputFile(pngStickerStream, pngStickerName);
        return this;
    }

    @Override
    public String toString() {
        return "UploadStickerFile{" +
                "userId=" + userId +
                ", pngSticker=" + pngSticker +
                '}';
    }
}
