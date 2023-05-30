package org.telegram.telegrambots.meta.api.methods.stickers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.Arrays;

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
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class UploadStickerFile extends PartialBotApiMethod<File> {
    public static final String PATH = "uploadStickerFile";

    public static final String USERID_FIELD = "user_id";
    public static final String STICKER_FORMAT_FIELD = "sticker_format";
    public static final String STICKER_FIELD = "sticker";

    @NonNull
    private Long userId; ///< User identifier of sticker file owner
    /**
     * Format of the sticker, must be one of “static”, “animated”, “video”
     */
    @NonNull
    private String stickerFormat;
    /**
     * 	A file with the sticker in .WEBP, .PNG, .TGS, or .WEBM format.
     * 	See <a href="https://core.telegram.org/stickers"/a> for technical requirements.
     */
    @NonNull
    private InputFile sticker; ///< New sticker file

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public File deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, File.class);
    }

    /**
     * @deprecated Use {{@link #getSticker()}}
     */
    @JsonIgnore
    @Deprecated
    public InputFile getPngSticker() {
        return sticker;
    }

    /**
     * @deprecated Use {{@link #setSticker(InputFile)}}
     */
    @JsonIgnore
    @Deprecated
    public void setPngSticker(InputFile pngSticker) {
        this.sticker = pngSticker;
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

    public static class UploadStickerFileBuilder {

        @Tolerate
        @Deprecated
        public UploadStickerFileBuilder pngSticker(InputFile pngSticker) {
            this.sticker = pngSticker;
            return this;
        }
    }
}
