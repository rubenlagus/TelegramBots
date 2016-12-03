package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(URL_FIELD)
    private String url; ///< Webhook URL, may be empty if webhook is not set up
    @JsonProperty(HASCUSTOMCERTIFICATE_FIELD)
    private Boolean hasCustomCertificate; ///< True, if a custom certificate was provided for webhook certificate checks
    @JsonProperty(PENDINGUPDATESCOUNT_FIELD)
    private Integer pendingUpdatesCount; ///< Number updates awaiting delivery
    @JsonProperty(LASTERRORDATE_FIELD)
    private Integer lastErrorDate; ///< Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook
    @JsonProperty(LASTERRORMESSAGE_FIELD)
    private String lastErrorMessage; ///< Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook
    @JsonProperty(MAXCONNECTIONS_FIELD)
    private Integer maxConnections; ///< Optional. Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
    @JsonProperty(ALLOWEDUPDATES_FIELD)
    private List<String> allowedUpdates; ///< Optional. A list of update types the bot is subscribed to. Defaults to all update types

    public WebhookInfo() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public boolean isHasCustomCertificate() {
        return hasCustomCertificate;
    }

    public int getPendingUpdatesCount() {
        return pendingUpdatesCount;
    }

    public int getLastErrorDate() {
        return lastErrorDate;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public List<String> getAllowedUpdates() {
        return allowedUpdates;
    }
}
