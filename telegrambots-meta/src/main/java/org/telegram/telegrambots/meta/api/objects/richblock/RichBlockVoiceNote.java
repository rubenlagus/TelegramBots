package org.telegram.telegrambots.meta.api.objects.richblock;

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
import org.telegram.telegrambots.meta.api.objects.Voice;

/**
 * @author Ruben Bermudez
 * @version 10.1
 * A block with a voice note, corresponding to the HTML tag &lt;audio&gt;.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class RichBlockVoiceNote implements RichBlock {
    public static final String TYPE = "voice_note";
    private static final String TYPE_FIELD = "type";
    private static final String VOICE_NOTE_FIELD = "voice_note";
    private static final String CAPTION_FIELD = "caption";

    /**
     * Type of the block, always "voice_note"
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = TYPE;

    /**
     * The voice note
     */
    @JsonProperty(VOICE_NOTE_FIELD)
    @NonNull
    private Voice voiceNote;

    /**
     * Optional. Caption of the block
     */
    @JsonProperty(CAPTION_FIELD)
    private RichBlockCaption caption;
}
