package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * A button.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextButton implements RichText {
    public static final String TYPE = "button";
    private static final String TYPE_FIELD = "type";
    private static final String BUTTON_FIELD = "button";

    /**
     * Type of the rich text, always "button"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The button
     */
    @JsonProperty(BUTTON_FIELD)
    @NonNull
    private RichMessageButton button;
}
