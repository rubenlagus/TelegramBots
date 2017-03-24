package org.telegram.telegrambots.api.objects;



import org.telegram.telegrambots.api.interfaces.BotApiObject;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 2.4
 * @brief Contains information about the current status of a webhook.
 * @date 12 of August of 2016
 */
public class WebhookInfo implements BotApiObject {

    private static final String URL_FIELD = "url";
    private static final String HASCUSTOMCERTIFICATE_FIELD = "has_custom_certificate";
    private static final String PENDINGUPDATESCOUNT_FIELD = "pending_updates_count";
    private static final String MAXCONNECTIONS_FIELD = "max_connections";
    private static final String ALLOWEDUPDATES_FIELD = "allowed_updates";
    private static final String LASTERRORDATE_FIELD = "last_error_date";
    private static final String LASTERRORMESSAGE_FIELD = "last_error_message";


    private String url; ///< Webhook URL, may be empty if webhook is not set up

    private Boolean has_custom_certificate; ///< True, if a custom certificate was provided for webhook certificate checks

    private Integer pending_updates_count; ///< Number updates awaiting delivery

    private Integer last_error_date; ///< Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook

    private String last_error_message; ///< Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook

    private Integer max_connections; ///< Optional. Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery

    private List<String> allowed_updates; ///< Optional. A list of update types the bot is subscribed to. Defaults to all update types

    public WebhookInfo() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public boolean isHasCustomCertificate() {
        return has_custom_certificate;
    }

    public int getPendingUpdatesCount() {
        return pending_updates_count;
    }

    public int getLastErrorDate() {
        return last_error_date;
    }

    public String getLastErrorMessage() {
        return last_error_message;
    }

    public Integer getMaxConnections() {
        return max_connections;
    }

    public List<String> getAllowedUpdates() {
        return allowed_updates;
    }
}
