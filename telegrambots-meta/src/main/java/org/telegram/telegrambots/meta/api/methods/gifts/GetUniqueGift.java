package org.telegram.telegrambots.meta.api.methods.gifts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGift;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * Returns information about a unique gift by its name.
 * @version 10.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUniqueGift extends BotApiMethod<UniqueGift> {
    public static final String PATH = "getUniqueGift";

    private static final String NAME_FIELD = "name";

    /**
     * Unique name of the gift
     */
    @JsonProperty(NAME_FIELD)
    @NonNull
    private String name;

    @Override
    public void validate() throws TelegramApiValidationException {
        if (name == null || name.isEmpty()) {
            throw new TelegramApiValidationException("Name can't be empty", this);
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public UniqueGift deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, UniqueGift.class);
    }
}