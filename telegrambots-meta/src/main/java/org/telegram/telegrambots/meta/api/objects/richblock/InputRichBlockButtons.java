package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.objects.richtext.RichMessageButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.3
 * A block containing a list of buttons that are shown in one row,
 * corresponding to the custom HTML tag &lt;tg-button-row&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRichBlockButtons implements InputRichBlock {
    public static final String TYPE = "buttons";
    private static final String TYPE_FIELD = "type";
    private static final String BUTTONS_FIELD = "buttons";
    private static final String ALIGN_FIELD = "align";

    /**
     * Type of the block, always "buttons"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * List of 1-8 buttons to send
     */
    @JsonProperty(BUTTONS_FIELD)
    @NonNull
    private List<RichMessageButton> buttons;

    /**
     * Optional. Horizontal alignment of the buttons.
     * Currently, must be one of "left", "center", or "right".
     */
    @JsonProperty(ALIGN_FIELD)
    private String align;

    @Override
    public void validate() throws TelegramApiValidationException {
        for (RichMessageButton button : buttons) {
            button.validate();
        }
    }
}
