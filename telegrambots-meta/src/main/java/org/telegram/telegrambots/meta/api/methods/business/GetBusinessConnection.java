package org.telegram.telegrambots.meta.api.methods.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 7.2
 * Use this method to get information about the connection of the bot with a business account.
 * Returns a BusinessConnection object on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBusinessConnection extends BotApiMethod<BusinessConnection> {
    public static final String PATH = "getBusinessConnection";

    private static final String BUSINESS_CONNECTION_ID_FIELD = "business_connection_id";

    /**
     * Unique identifier of the business connection
     */
    @JsonProperty(BUSINESS_CONNECTION_ID_FIELD)
    @NonNull
    private String businessConnectionId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public BusinessConnection deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, BusinessConnection.class);
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (businessConnectionId.isEmpty()) {
            throw new TelegramApiValidationException("BusinessConnectionId parameter can't be empty string", this);
        }
    }
}
