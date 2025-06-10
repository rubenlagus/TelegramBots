package org.telegram.telegrambots.meta.api.objects.stories.area;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * Describes the type of a clickable area on a story. Currently, it can be one of
 * StoryAreaTypeLocation
 * StoryAreaTypeSuggestedReaction
 * StoryAreaTypeLink
 * StoryAreaTypeWeather
 * StoryAreaTypeUniqueGift
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StoryAreaTypeLocation.class, name = "location"),
        @JsonSubTypes.Type(value = StoryAreaTypeSuggestedReaction.class, name = "suggested_reaction"),
        @JsonSubTypes.Type(value = StoryAreaTypeLink.class, name = "link"),
        @JsonSubTypes.Type(value = StoryAreaTypeWeather.class, name = "weather"),
        @JsonSubTypes.Type(value = StoryAreaTypeUniqueGift.class, name = "unique_gift")
})
public abstract class StoryAreaType implements Validable, BotApiObject {
    public static final String TYPE_FIELD = "type";

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();
}
