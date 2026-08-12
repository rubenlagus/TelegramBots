package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
 * A plain unformatted text.
 * <p>
 * The Bot API has no {@code "plain"} rich text type — plain text is carried on the wire as a bare
 * JSON string. This class only exists to give plain text a place in the {@link RichText} hierarchy,
 * so it is serialized as a bare string by {@link RichTextPlainSerializer} and never emits a type
 * field.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = RichTextPlainSerializer.class)
public class RichTextPlain implements RichText {
    public static final String TYPE = "plain";
    private static final String TYPE_FIELD = "type";
    private static final String TEXT_FIELD = "text";

    /**
     * Logical type name, always "plain". Not serialized — the wire format is a bare JSON string.
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The plain text
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;
}
