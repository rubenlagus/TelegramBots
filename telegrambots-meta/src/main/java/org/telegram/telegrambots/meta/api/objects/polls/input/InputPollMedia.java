package org.telegram.telegrambots.meta.api.objects.polls.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaLivePhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaLocation;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVenue;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * This object represents the content of a poll description or a quiz explanation to be sent.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InputMediaAnimation.class, name = "animation"),
        @JsonSubTypes.Type(value = InputMediaAudio.class, name = "audio"),
        @JsonSubTypes.Type(value = InputMediaDocument.class, name = "document"),
        @JsonSubTypes.Type(value = InputMediaLivePhoto.class, name = "live_photo"),
        @JsonSubTypes.Type(value = InputMediaLocation.class, name = "location"),
        @JsonSubTypes.Type(value = InputMediaPhoto.class, name = "photo"),
        @JsonSubTypes.Type(value = InputMediaVenue.class, name = "venue"),
        @JsonSubTypes.Type(value = InputMediaVideo.class, name = "video")
})
public interface InputPollMedia extends Validable, BotApiObject {
}
