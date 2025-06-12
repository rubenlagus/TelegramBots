package org.telegram.telegrambots.meta.api.objects.stories.input;

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
 * This object describes the content of a story to post. Currently, it can be one of
 * InputStoryContentPhoto
 * InputStoryContentVideo
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
        @JsonSubTypes.Type(value = InputStoryContentPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = InputStoryContentVideo.class, name = "video")
})
public abstract class InputStoryContent implements Validable, BotApiObject {
    public static final String TYPE_FIELD = "type";

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();
}
