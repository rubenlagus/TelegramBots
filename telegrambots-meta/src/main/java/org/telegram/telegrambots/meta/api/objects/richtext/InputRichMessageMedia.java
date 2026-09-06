package org.telegram.telegrambots.meta.api.objects.richtext;

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
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

/**
 * @author Ruben Bermudez
 * @version 10.2
 * Describes a media element embedded in an outgoing rich message.
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
public class InputRichMessageMedia implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String MEDIA_FIELD = "media";

    /**
     * Unique identifier of the media used in a tg://photo?id=, tg://video?id=,
     * tg://document?id=, or tg://audio?id= link.
     * 1-64 characters, only A-Z, a-z, 0-9, _ and - are allowed.
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id;

    /**
     * The media to be sent. Everything except the media itself and its properties is ignored.
     */
    @JsonProperty(MEDIA_FIELD)
    @NonNull
    private InputMedia media;
}
