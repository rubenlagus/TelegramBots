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
 * @version 10.1
 * A reference.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextReference implements RichText {
    public static final String TYPE = "reference";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";
    private static final String NAME_FIELD = "name";

    /**
     * Type of the rich text, always "reference"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * Text of the reference
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private RichText text;

    /**
     * The name of the reference
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;
}
