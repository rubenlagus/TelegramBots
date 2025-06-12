package org.telegram.telegrambots.meta.api.objects.gifts.owned;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Ruben Bermudez
 * @version 9.0
 *
 * This object describes a gift received and owned by a user or a chat. Currently, it can be one of
 * OwnedGiftRegular
 * OwnedGiftUnique
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = Void.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OwnedGiftRegular.class, name = "regular"),
        @JsonSubTypes.Type(value = OwnedGiftUnique.class, name = "unique")
})
public abstract class OwnedGift implements BotApiObject {
    public static final String TYPE_FIELD = "type";
    public static final String OWNED_GIFT_ID_FIELD = "owned_gift_id";
    public static final String SENDER_USER_FIELD = "sender_user";
    public static final String SEND_DATE_FIELD = "send_date";
    public static final String IS_SAVED_FIELD = "is_saved";

    /**
     * Optional. Unique identifier of the gift for the bot; for gifts received on behalf of business accounts only
     */
    @JsonProperty(OWNED_GIFT_ID_FIELD)
    private String ownedGiftId;
    
    /**
     * Optional. Sender of the gift if it is a known user
     */
    @JsonProperty(SENDER_USER_FIELD)
    private User senderUser;
    
    /**
     * Date the gift was sent in Unix time
     */
    @JsonProperty(SEND_DATE_FIELD)
    @NonNull
    private Integer sendDate;
    
    /**
     * Optional. True, if the gift is displayed on the account's profile page; for gifts received on behalf of business accounts only
     */
    @JsonProperty(IS_SAVED_FIELD)
    private Boolean isSaved;

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();
}
