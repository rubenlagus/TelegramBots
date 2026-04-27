package org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 9.6
 *
 * This object defines the parameters for the creation of a managed bot.
 * Information about the created bot will be shared with the bot using the update managed_bot
 * and a Message with the field managed_bot_created.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyboardButtonRequestManagedBot implements BotApiObject {
    private static final String REQUEST_ID_FIELD = "request_id";
    private static final String SUGGESTED_NAME_FIELD = "suggested_name";
    private static final String SUGGESTED_USERNAME_FIELD = "suggested_username";

    /**
     * Signed 32-bit identifier of the request. Must be unique within the message
     */
    @JsonProperty(REQUEST_ID_FIELD)
    @NonNull
    private Integer requestId;
    /**
     * Optional.
     * Suggested name for the bot
     */
    @JsonProperty(SUGGESTED_NAME_FIELD)
    private String suggestedName;
    /**
     * Optional.
     * Suggested username for the bot
     */
    @JsonProperty(SUGGESTED_USERNAME_FIELD)
    private String suggestedUsername;
}
