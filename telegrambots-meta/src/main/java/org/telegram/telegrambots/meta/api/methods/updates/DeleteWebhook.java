package org.telegram.telegrambots.meta.api.methods.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to receive incoming updates using long polling (wiki). An Array of Update
 * objects is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteWebhook extends BotApiMethod<Boolean>{
    public static final String PATH = "deleteWebhook";

    private static final String DROPPENDINGUPDATES_FIELD = "drop_pending_updates";

    @JsonProperty(DROPPENDINGUPDATES_FIELD)
    private Boolean dropPendingUpdates; ///< Optional. Pass True to drop all pending updates

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponseDefault(answer, "Error deleting webhook");
    }

    @Override
    public void validate() throws TelegramApiValidationException {
    }
}
