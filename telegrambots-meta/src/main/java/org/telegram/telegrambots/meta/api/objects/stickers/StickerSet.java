package org.telegram.telegrambots.meta.api.objects.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a sticker set.
 */
public class StickerSet implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String TITLE_FIELD = "title";
    private static final String CONTAINSMASKS_FIELD = "contains_masks";
    private static final String STICKERS_FIELD = "stickers";
    private static final String ISANIMATED_FIELD = "is_animated";

    @JsonProperty(NAME_FIELD)
    private String name;
    @JsonProperty(TITLE_FIELD)
    private String title;
    @JsonProperty(CONTAINSMASKS_FIELD)
    private Boolean containsMasks;
    @JsonProperty(STICKERS_FIELD)
    private List<Sticker> stickers;
    @JsonProperty(ISANIMATED_FIELD)
    private Boolean isAnimated;

    public StickerSet() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getContainsMasks() {
        return containsMasks;
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public Boolean getAnimated() {
        return isAnimated;
    }

    @Override
    public String toString() {
        return "StickerSet{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", containsMasks=" + containsMasks +
                ", stickers=" + stickers +
                ", isAnimated=" + isAnimated +
                '}';
    }
}
