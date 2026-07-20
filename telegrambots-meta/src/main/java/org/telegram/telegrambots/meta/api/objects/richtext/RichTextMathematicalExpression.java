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
 * A mathematical expression.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichTextMathematicalExpression implements RichText {
    public static final String TYPE = "mathematical_expression";
    private static final String TYPE_FIELD = "type";
    private static final String EXPRESSION_FIELD = "expression";

    /**
     * Type of the rich text, always "mathematical_expression"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The expression in LaTeX format
     */
    @JsonProperty(EXPRESSION_FIELD)
    @NonNull
    private String expression;
}
