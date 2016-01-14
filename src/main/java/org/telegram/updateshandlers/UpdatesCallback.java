package org.telegram.updateshandlers;

import org.telegram.api.objects.Update;
import org.telegram.api.methods.BotApiMethod;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Callback to handle updates. Must support both, single update and List of updates
 * @date 20 of June of 2015
 */
public interface UpdatesCallback {
    /**
     * This method is called when receiving updates via org.telegram.api.methods.GetUpdates method
     * @param update Update received
     */
    void onUpdateReceived(Update update);

    /**
     * This method is called when receiving updates via webhook
     * @param update Update received
     */
    BotApiMethod onWebhookUpdateReceived(Update update);
}
