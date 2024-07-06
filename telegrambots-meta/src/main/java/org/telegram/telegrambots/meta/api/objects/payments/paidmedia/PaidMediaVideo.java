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
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartner;

/**
 * @author Ruben Bermudez
 * @version 7.6
 *
 * The paid media is a video.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class PaidMediaVideo implements TransactionPartner {
    private static final String TYPE_FIELD = "type";
    private static final String VIDEO_FIELD = "video";

    /**
     * Type of the paid media, always “video”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "video";
    /**
     * The video
     */
    @JsonProperty(VIDEO_FIELD)
    @NonNull
    private Video video;
}
