package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Arrays;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to upload a file with a sticker for later use in the createNewStickerSet, addStickerToSet,
 * or replaceStickerInSet methods (the file can be used multiple times).
 * Returns the uploaded File on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadStickerFile extends PartialBotApiMethod<File> {
    public static final String PATH = "uploadStickerFile";

    public static final String USERID_FIELD = "user_id";
    public static final String STICKER_FORMAT_FIELD = "sticker_format";
    public static final String STICKER_FIELD = "sticker";

    /**
     * User identifier of sticker file owner
     */
    @NonNull
    private Long userId;
    /**
     * Format of the sticker, must be one of “static”, “animated”, “video”
     */
    @NonNull
    private String stickerFormat;
    /**
     * New sticker file
     * A file with the sticker in .WEBP, .PNG, .TGS, or .WEBM format.
     * See <a href="https://core.telegram.org/stickers"/a> for technical requirements.
     */
    @NonNull
    private InputFile sticker;

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
        if (stickerFormat.isEmpty() || !Arrays.asList("static", "animated", "video").contains(stickerFormat)) {
            throw new TelegramApiValidationException("Sticker Format must be one of 'static', 'animated', 'video'", this);
        }

        sticker.validate();
    }
}
