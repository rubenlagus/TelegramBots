package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A concatenation of multiple RichText nodes, corresponding to a JSON array of RichText.
 * Serialized as a plain JSON array (no type field).
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@JsonSerialize(using = RichTextConcatSerializer.class)
public class RichTextConcat implements RichText {

    /**
     * Logical type name. Not serialized — the wire format is a bare JSON array.
     */
    @JsonIgnore
    public static final String TYPE = "concat";

    /**
     * The individual text nodes in this concatenation.
     */
    @NonNull
    @Singular
    private List<RichText> texts;

    @Override
    @JsonIgnore
    public String getType() {
        return TYPE;
    }
}
