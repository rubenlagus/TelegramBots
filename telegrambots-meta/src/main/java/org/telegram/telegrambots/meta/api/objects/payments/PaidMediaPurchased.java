package org.telegram.telegrambots.meta.api.objects.payments;

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
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 7.10
 * This object contains information about a paid media purchase.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaidMediaPurchased implements BotApiObject {
    private static final String FROM_FIELD = "from";
    private static final String PAID_MEDIA_PAYLOAD_FIELD = "paid_media_payload";

    /**
     * User who purchased the media
     */
    @JsonProperty(FROM_FIELD)
    @NonNull
    private User user;
    /**
     * Bot-specified paid media payload
     */
    @JsonProperty(PAID_MEDIA_PAYLOAD_FIELD)
    @NonNull
    private String paidMediaPayload;
}
