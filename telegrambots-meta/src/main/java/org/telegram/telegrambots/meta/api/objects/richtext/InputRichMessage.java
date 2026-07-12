package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * Describes a rich message to be sent. Exactly one of the fields html or markdown must be used.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputRichMessage implements BotApiObject, Validable {
    private static final String HTML_FIELD = "html";
    private static final String MARKDOWN_FIELD = "markdown";
    private static final String IS_RTL_FIELD = "is_rtl";
    private static final String SKIP_ENTITY_DETECTION_FIELD = "skip_entity_detection";

    /**
     * Optional. Content of the rich message to send described using HTML formatting.
     */
    @JsonProperty(HTML_FIELD)
    private String html;

    /**
     * Optional. Content of the rich message to send described using Markdown formatting.
     */
    @JsonProperty(MARKDOWN_FIELD)
    private String markdown;

    /**
     * Optional. Pass True if the rich message must be shown right-to-left.
     */
    @JsonProperty(IS_RTL_FIELD)
    private Boolean isRtl;

    /**
     * Optional. Pass True to skip automatic detection of entities in the text.
     */
    @JsonProperty(SKIP_ENTITY_DETECTION_FIELD)
    private Boolean skipEntityDetection;

    @Override
    public void validate() throws TelegramApiValidationException {
        if ((html == null || html.isEmpty()) && (markdown == null || markdown.isEmpty())) {
            throw new TelegramApiValidationException("Either html or markdown parameter must be provided", this);
        }
        if (html != null && !html.isEmpty() && markdown != null && !markdown.isEmpty()) {
            throw new TelegramApiValidationException("Only one of html or markdown parameter can be provided", this);
        }
    }
}
