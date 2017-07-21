package org.telegram.telegrambots.api.methods.stickers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;

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

    public static final String USERID_FIELD = "name";
    public static final String PNGSTICKER_FIELD = "png_sticker";

    private Integer userId; ///< User identifier of sticker file owner
    /**
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px. More info on Sending Files »
     */
    private java.io.File newPngStickerFile; ///< New sticker file
    private InputStream newPngStickerStream; ///< New sticker stream
    private String newPngStickerName; ///< New sticker stream name

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
        if (newPngStickerFile == null && newPngStickerStream == null) {
            throw new TelegramApiValidationException("file or stream must be present", this);
        }
        if (newPngStickerStream != null && (newPngStickerName == null || newPngStickerName.isEmpty())) {
            throw new TelegramApiValidationException("Stream name must be present", this);
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public UploadStickerFile setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public java.io.File getNewPngStickerFile() {
        return newPngStickerFile;
    }

    public UploadStickerFile setNewPngSticker(java.io.File newPngStickerFile) {
        this.newPngStickerFile = newPngStickerFile;
        return this;
    }

    public InputStream getNewPngStickerStream() {
        return newPngStickerStream;
    }

    public UploadStickerFile setNewPngSticker(String newPngStickerName, InputStream newPngStickerStream) {
        this.newPngStickerName = newPngStickerName;
        this.newPngStickerStream = newPngStickerStream;
        return this;
    }

    public String getNewPngStickerName() {
        return newPngStickerName;
    }

    @Override
    public String toString() {
        return "UploadStickerFile{" +
                "userId=" + userId +
                ", newPngStickerFile=" + newPngStickerFile +
                ", newPngStickerStream=" + newPngStickerStream +
                ", newPngStickerName='" + newPngStickerName + '\'' +
                '}';
    }
}
