package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;

/**
 * Describes the options used for link preview generation.
 * @author Ruben Bermudez
 * @version 7.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LinkPreviewOptions implements BotApiObject, Validable {
    private static final String IS_DISABLED_FIELD = "is_disabled";
    private static final String URL_FIELD = "url";
    private static final String PREFER_SMALL_MEDIA_FIELD = "prefer_small_media";
    private static final String PREFER_LARGE_MEDIA_FIELD = "prefer_large_media";
    private static final String SHOW_ABOVE_TEXT_FIELD = "show_above_text";

    /**
     * 	Optional.
     * 	True, if the link preview is disabled
     */
    @JsonProperty(IS_DISABLED_FIELD)
    private Boolean isDisabled;
    /**
     * Optional.
     * URL to use for the link preview. If empty, then the first URL found in the message text will be used
     */
    @JsonProperty(URL_FIELD)
    private String urlField;
    /**
     * Optional.
     * True, if the media in the link preview is supposed to be shrunk; ignored if the URL isn't explicitly specified or media size change isn't supported for the preview
     */
    @JsonProperty(PREFER_SMALL_MEDIA_FIELD)
    private Boolean preferSmallMedia;
    /**
     * Optional.
     * True, if the media in the link preview is suppposed to be enlarged; ignored if the URL isn't explicitly specified or media size change isn't supported for the preview
     */
    @JsonProperty(PREFER_LARGE_MEDIA_FIELD)
    private Boolean preferLargeMedia;
    /**
     * 	Optional.
     * 	True, if the link preview must be shown above the message text; otherwise, the link preview will be shown below the message text
     */
    @JsonProperty(SHOW_ABOVE_TEXT_FIELD)
    private Boolean showAboveText;
}
