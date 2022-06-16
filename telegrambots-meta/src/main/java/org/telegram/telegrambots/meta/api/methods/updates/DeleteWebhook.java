package org.telegram.telegrambots.meta.api.methods.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean;

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
public class DeleteWebhook extends BotApiMethodBoolean {
    public static final String PATH = "deleteWebhook";

    private static final String DROPPENDINGUPDATES_FIELD = "drop_pending_updates";

    @JsonProperty(DROPPENDINGUPDATES_FIELD)
    private Boolean dropPendingUpdates; ///< Optional. Pass True to drop all pending updates

    @Override
    public String getMethod() {
        return PATH;
    }
}
