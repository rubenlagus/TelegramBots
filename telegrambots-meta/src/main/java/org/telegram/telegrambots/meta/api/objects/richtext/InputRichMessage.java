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
import org.telegram.telegrambots.meta.api.objects.richblock.InputRichBlock;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * Describes a rich message to be sent.
 * Exactly one of the fields html, markdown, or blocks must be used.
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
    private static final String BLOCKS_FIELD = "blocks";
    private static final String MEDIA_FIELD = "media";

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

    /**
     * Optional. Content of the rich message to send described as a list of blocks
     */
    @JsonProperty(BLOCKS_FIELD)
    private List<InputRichBlock> blocks;

    /**
     * Optional. List of media that are specified in the markdown or html fields
     * using tg://photo?id=, tg://video?id=, tg://document?id=, and tg://audio?id= links
     */
    @JsonProperty(MEDIA_FIELD)
    private List<InputRichMessageMedia> media;

    @Override
    public void validate() throws TelegramApiValidationException {
        int providedCount = 0;
        if (html != null && !html.isEmpty()) {
            providedCount++;
        }
        if (markdown != null && !markdown.isEmpty()) {
            providedCount++;
        }
        if (blocks != null && !blocks.isEmpty()) {
            providedCount++;
        }
        if (providedCount == 0) {
            throw new TelegramApiValidationException("Exactly one of html, markdown or blocks parameter must be provided", this);
        }
        if (providedCount > 1) {
            throw new TelegramApiValidationException("Only one of html, markdown or blocks parameter can be provided", this);
        }
        if (blocks != null) {
            for (InputRichBlock block : blocks) {
                block.validate();
            }
        }
    }
}
