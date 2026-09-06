package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @version 10.2
 * A block with a mathematical expression in LaTeX format,
 * corresponding to the custom HTML tag &lt;tg-math-block&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRichBlockMathematicalExpression implements InputRichBlock {
    public static final String TYPE = "mathematical_expression";
    private static final String TYPE_FIELD = "type";
    private static final String EXPRESSION_FIELD = "expression";

    /**
     * Type of the block, always "mathematical_expression"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The mathematical expression in LaTeX format
     */
    @JsonProperty(EXPRESSION_FIELD)
    @NonNull
    private String expression;
}
