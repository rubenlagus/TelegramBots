package org.telegram.telegrambots.meta.api.objects.stickers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.List;

/**
 * This object represents a sticker set.
 * @author Ruben Bermudez
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StickerSet implements BotApiObject {
    private static final String STICKERTYPE_FIELD = "sticker_type";
    private static final String NAME_FIELD = "name";
    private static final String TITLE_FIELD = "title";
    private static final String CONTAINSMASKS_FIELD = "contains_masks";
    private static final String STICKERS_FIELD = "stickers";
    private static final String ISANIMATED_FIELD = "is_animated";
    private static final String ISVIDEO_FIELD = "is_video";
    public static final String THUMBNAIL_FIELD = "thumbnail";

    /**
     * Type of stickers in the set, currently one of “regular”, “mask”, “custom_emoji”
     */
    @JsonProperty(STICKERTYPE_FIELD)
    private String stickerType;
    /**
     * Sticker set name
     */
    @JsonProperty(NAME_FIELD)
    private String name;
    /**
     * Sticker set title
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * True, if the sticker set contains animated stickers
     *
     * @deprecated Use {@link #getStickerType()} or {@link #isMask()}
     */
    @JsonProperty(CONTAINSMASKS_FIELD)
    @Deprecated
    private Boolean containsMasks;
    /**
     * True, if the sticker set contains masks
     */
    @JsonProperty(STICKERS_FIELD)
    private List<Sticker> stickers;
    /**
     * List of all set stickers
     */
    @JsonProperty(ISANIMATED_FIELD)
    private Boolean isAnimated;
    /**
     * True, if the sticker set contains video stickers
     */
    @JsonProperty(ISVIDEO_FIELD)
    private Boolean isVideo;
    /**
     * Optional.
     * Sticker set thumbnail in the .WEBP, .TGS, or .WEBM format
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private PhotoSize thumbnail;

    public boolean isRegularSticker() {
        return "regular".equals(stickerType);
    }
    public boolean isMask() {
        return "mask".equals(stickerType);
    }

    public boolean isCustomEmoji() {
        return "custom_emoji".equals(stickerType);
    }


    /**
     * @deprecated Use {{@link #getThumbnail()}}
     */
    @JsonIgnore
    @Deprecated
    public PhotoSize getThumb() {
        return thumbnail;
    }
}
