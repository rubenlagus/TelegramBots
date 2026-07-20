package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A text covered by a spoiler.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextSpoiler implements RichText {
    public static final String TYPE = "spoiler";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";

    /**
     * Type of the rich text, always "spoiler"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The text
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;
}
