package org.telegram.telegrambots.meta.api.methods.stickers;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to upload a .png file with a sticker for later use in createNewStickerSet and addStickerToSet
 * methods (can be used multiple times). Returns the uploaded File on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadStickerFile extends PartialBotApiMethod<File> {
    public static final String PATH = "uploadStickerFile";

    public static final String USERID_FIELD = "user_id";
    public static final String PNGSTICKER_FIELD = "png_sticker";

    @NonNull
    private Long userId; ///< User identifier of sticker file owner
    /**
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px. More info on Sending Files »
     */
    @NonNull
    private InputFile pngSticker; ///< New sticker file

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public File deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, File.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }

        pngSticker.validate();
    }
}
