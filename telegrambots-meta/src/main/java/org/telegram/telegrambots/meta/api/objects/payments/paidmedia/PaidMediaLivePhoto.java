package org.telegram.telegrambots.meta.api.objects.payments.paidmedia;

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
import org.telegram.telegrambots.meta.api.objects.LivePhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 10.0
 * Describes a paid media with a live photo.
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
public class PaidMediaLivePhoto implements PaidMedia {
    private static final String TYPE_FIELD = "type";
    private static final String LIVE_PHOTO_FIELD = "live_photo";

    /**
     * Type of the paid media, always “live_photo”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "live_photo";
    /**
     * The photo
     */
    @JsonProperty(LIVE_PHOTO_FIELD)
    private LivePhoto livePhoto;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (livePhoto == null) {
            throw new TelegramApiValidationException("LivePhoto can't be null", this);
        }
    }
}
