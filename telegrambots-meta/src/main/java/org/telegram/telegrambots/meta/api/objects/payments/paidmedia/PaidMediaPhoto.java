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
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.payments.transactionpartner.TransactionPartner;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 7.6
 *
 * The paid media is a photo.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class PaidMediaPhoto implements PaidMedia {
    private static final String TYPE_FIELD = "type";
    private static final String PHOTO_FIELD = "photo";

    /**
     * Type of the paid media, always “photo”
     */
    @JsonProperty(TYPE_FIELD)
    private final String type = "photo";
    /**
     * The photo
     */
    @JsonProperty(PHOTO_FIELD)
    @NonNull
    private List<PhotoSize> photo;
}
