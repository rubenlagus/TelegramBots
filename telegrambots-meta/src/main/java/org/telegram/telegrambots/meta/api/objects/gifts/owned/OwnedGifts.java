package org.telegram.telegrambots.meta.api.objects.gifts.owned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * Contains the list of gifts received and owned by a user or a chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnedGifts implements BotApiObject {
    private static final String TOTAL_COUNT_FIELD = "total_count";
    private static final String GIFTS_FIELD = "gifts";
    private static final String NEXT_OFFSET_FIELD = "next_offset";

    /**
     * The total number of gifts owned by the user or the chat
     */
    @JsonProperty(TOTAL_COUNT_FIELD)
    @NonNull
    private Integer totalCount;

    /**
     * The list of gifts
     */
    @JsonProperty(GIFTS_FIELD)
    @NonNull
    private List<OwnedGift> gifts;

    /**
     * Optional. Offset for the next request. If empty, then there are no more results
     */
    @JsonProperty(NEXT_OFFSET_FIELD)
    private String nextOffset;
}
