package org.telegram.telegrambots.meta.api.objects.payments.paidmedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.6
 *
 * Describes the paid media added to a message.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaidMediaInfo implements BotApiObject {
    private static final String STAR_COUNT_FIELD = "star_count";
    private static final String PAID_MEDIA_FIELD = "paid_media";

    /**
     * The number of Telegram Stars that must be paid to buy access to the media
     */
    @JsonProperty(STAR_COUNT_FIELD)
    @NonNull
    private String starCount;
    /**
     * Information about the paid media
     */
    @JsonProperty(PAID_MEDIA_FIELD)
    @NonNull
    private List<PaidMedia> paidMedia;
}
