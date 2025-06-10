package org.telegram.telegrambots.meta.api.objects.photo.input;

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
 * @author Ruben Bermudez
 * @version 9.0
 *
 * This object describes a profile photo to set. Currently, it can be one of
 * InputProfilePhotoStatic
 * InputProfilePhotoAnimated
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
        @JsonSubTypes.Type(value = InputProfilePhotoStatic.class, name = "static"),
        @JsonSubTypes.Type(value = InputProfilePhotoAnimated.class, name = "animated")
})
public abstract class InputProfilePhoto implements Validable, BotApiObject {
    public static final String TYPE_FIELD = "type";

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();
}
