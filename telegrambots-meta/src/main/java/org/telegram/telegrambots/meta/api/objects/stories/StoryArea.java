package org.telegram.telegrambots.meta.api.objects.stories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.AreaPosition;
import org.telegram.telegrambots.meta.api.objects.stories.area.StoryAreaType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Describes a clickable area on a story media.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoryArea implements BotApiObject, Validable {
    private static final long serialVersionUID = -2095618507227580572L;

    private static final String POSITION_FIELD = "position";
    private static final String TYPE_FIELD = "type";

    /**
     * Position of the area
     */
    @JsonProperty(POSITION_FIELD)
    @NonNull
    private AreaPosition position;

    /**
     * Type of the area
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private StoryAreaType type;

    public StoryArea(StoryAreaPosition position, StoryAreaType type) {
        this.position = position;
        this.type = type;
    }

    /** Returns the position using the legacy story-specific type. */
    @JsonProperty(POSITION_FIELD)
    public StoryAreaPosition getPosition() {
        if (position == null || position instanceof StoryAreaPosition) {
            return (StoryAreaPosition) position;
        }
        StoryAreaPosition legacyPosition = new StoryAreaPosition(position.getXPercentage(), position.getYPercentage(), position.getWidthPercentage(),
                position.getHeightPercentage(), position.getRotationAngle(), position.getCornerRadiusPercentage());
        position = legacyPosition;
        return legacyPosition;
    }

    @JsonProperty(POSITION_FIELD)
    public void setPosition(AreaPosition position) {
        this.position = position;
    }

    /** Retained for binary compatibility with the legacy story-specific setter. */
    @JsonIgnore
    public void setPosition(StoryAreaPosition position) {
        this.position = position;
    }

    /** Returns the position using the general-purpose area type. */
    @JsonIgnore
    public AreaPosition getAreaPosition() {
        return position;
    }

    @JsonIgnore
    public void setAreaPosition(AreaPosition position) {
        this.position = position;
    }

    public static class StoryAreaBuilder {
        public StoryAreaBuilder position(AreaPosition position) {
            this.position = position;
            return this;
        }

        public StoryAreaBuilder position(StoryAreaPosition position) {
            this.position = position;
            return this;
        }

        public StoryAreaBuilder areaPosition(AreaPosition position) {
            this.position = position;
            return this;
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (position == null) {
            throw new TelegramApiValidationException("Position can't be null", this);
        }
        position.validate();
        type.validate();
    }
}
