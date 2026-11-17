package org.telegram.telegrambots.meta.api.methods.verification;

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
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.util.Validations;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 8.2
 *
 * Verifies a user on behalf of the organization which is represented by the bot.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyUser extends BotApiMethodBoolean {
    public static final String PATH = "verifyUser";

    private static final String USER_ID_FIELD = "user_id";
    private static final String CUSTOM_DESCRIPTION_FIELD = "custom_description";
    private static final String CUSTOM_DESCRIPTION_PARSE_MODE_FIELD = "custom_description_parse_mode";
    private static final String CUSTOM_DESCRIPTION_ENTITIES_FIELD = "custom_description_entities";

    /**
     * Unique identifier of the target user
     */
    @JsonProperty(USER_ID_FIELD)
    @NonNull
    private Long userId;
    /**
     * Optional
     * Custom description for the verification; 0-70 characters.
     * Must be empty if the organization isn't allowed to provide a custom verification description.
     */
    @JsonProperty(CUSTOM_DESCRIPTION_FIELD)
    private String customDescription;
    /**
     * Optional. Mode for parsing entities in the custom description. Only text_link entities are supported.
     */
    @JsonProperty(CUSTOM_DESCRIPTION_PARSE_MODE_FIELD)
    private String customDescriptionParseMode;
    /**
     * Optional. Entities in the custom description, instead of a parse mode. Only text_link entities are supported.
     */
    @JsonProperty(CUSTOM_DESCRIPTION_ENTITIES_FIELD)
    private List<MessageEntity> customDescriptionEntities;

    @Tolerate
    public VerifyUser(@NonNull Long userId, String customDescription) {
        this.userId = userId;
        this.customDescription = customDescription;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        Validations.requiredUserId(userId, this);
        if (customDescription != null && customDescriptionParseMode == null && customDescription.length() > 70) {
            throw new TelegramApiValidationException("Custom description must be between 0 and 70 characters long", this);
        }
        if (customDescriptionParseMode != null && customDescriptionEntities != null && !customDescriptionEntities.isEmpty()) {
            throw new TelegramApiValidationException("Custom description parse mode can't be enabled if entities are provided", this);
        }
        if (customDescriptionEntities != null) {
            for (MessageEntity entity : customDescriptionEntities) {
                if (entity == null || !"text_link".equals(entity.getType())) {
                    throw new TelegramApiValidationException("Only text_link custom description entities are supported", this);
                }
            }
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }
}
