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
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

/**
 * This object represents a sticker.
 * @author Ruben Bermudez
 * @version 6.1
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sticker implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String TYPE_FIELD = "type";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String THUMBNAIL_FIELD = "thumbnail";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String EMOJI_FIELD = "emoji";
    private static final String SETNAME_FIELD = "set_name";
    private static final String MASKPOSITON_FIELD = "mask_position";
    private static final String ISANIMATED_FIELD = "is_animated";
    private static final String ISVIDEO_FIELD = "is_video";
    private static final String PREMIUMANIMATION_FIELD = "premium_animation";
    private static final String CUSTOMEMOJIID_FIELD = "custom_emoji_id";
    private static final String NEEDS_REPAINTING_FIELD = "needs_repainting";

    /**
     * Identifier for this file, which can be used to download or reuse the file
     */
    @JsonProperty(FILEID_FIELD)
    private String fileId;
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    /**
     * Type of the sticker, currently one of “regular”, “mask”, “custom_emoji”.
     * The type of the sticker is independent of its format, which is determined by the fields is_animated and is_video.
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    /**
     * Sticker width
     */
    @JsonProperty(WIDTH_FIELD)
    private Integer width;
    /**
     * Sticker height
     */
    @JsonProperty(HEIGHT_FIELD)
    private Integer height;
    /**
     * Optional.
     * Sticker thumbnail in .webp or .jpg format
     */
    @JsonProperty(THUMBNAIL_FIELD)
    private PhotoSize thumbnail;
    /**
     * Optional.
     * File size
     */
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize;
    /**
     * Optional.
     * Emoji associated with the sticker
     */
    @JsonProperty(EMOJI_FIELD)
    private String emoji;
    /**
     * Optional.
     * Name of the sticker set to which the sticker belongs
     */
    @JsonProperty(SETNAME_FIELD)
    private String setName;
    /**
     * Optional.
     * For mask stickers, the position where the mask should be placed
     */
    @JsonProperty(MASKPOSITON_FIELD)
    private MaskPosition maskPosition;
    /**
     * True, if the sticker is animated
     */
    @JsonProperty(ISANIMATED_FIELD)
    private Boolean isAnimated;
    /**
     * True, if the sticker is a video sticker
     */
    @JsonProperty(ISVIDEO_FIELD)
    private Boolean isVideo;
    /**
     * Optional.
     * Premium animation for the sticker, if the sticker is premium
     */
    @JsonProperty(PREMIUMANIMATION_FIELD)
    private File premiumAnimation;
    /**
     * Optional.
     * For custom emoji stickers, unique identifier of the custom emoji
     */
    @JsonProperty(CUSTOMEMOJIID_FIELD)
    private String customEmojiId;

    /**.
     * Optional.
     * True, if the sticker must be repainted to a text color in messages, the color of the Telegram Premium badge
     * in emoji status, white color on chat photos, or another appropriate color in other places
     */
    @JsonProperty(NEEDS_REPAINTING_FIELD)
    private Boolean needsRepainting;

    /**
     * @deprecated Use {{@link #getThumbnail()}}
     */
    @JsonIgnore
    @Deprecated
    public PhotoSize getThumb() {
        return thumbnail;
    }

    /**
     * @deprecated Use {{@link #setThumbnail(PhotoSize)}}
     */
    @JsonIgnore
    @Deprecated
    public void setThumb(PhotoSize thumb) {
        this.thumbnail = thumb;
    }
}
