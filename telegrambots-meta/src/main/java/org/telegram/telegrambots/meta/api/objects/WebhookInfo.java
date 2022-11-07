package org.telegram.telegrambots.meta.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.List;

/**
 * Contains information about the current status of a webhook.
 * @author Ruben Bermudez
 * @version 2.4
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebhookInfo implements BotApiObject {

    private static final String URL_FIELD = "url";
    private static final String HASCUSTOMCERTIFICATE_FIELD = "has_custom_certificate";
    private static final String PENDINGUPDATECOUNT_FIELD = "pending_update_count";
    private static final String MAXCONNECTIONS_FIELD = "max_connections";
    private static final String ALLOWEDUPDATES_FIELD = "allowed_updates";
    private static final String LASTERRORDATE_FIELD = "last_error_date";
    private static final String LASTERRORMESSAGE_FIELD = "last_error_message";
    private static final String IPADDRESS_FIELD = "ip_address";
    private static final String LASTSYNCHRONIZATIONERRORDATE_FIELD = "last_synchronization_error_date";

    /**
     * Webhook URL, may be empty if webhook is not set up
     */
    @JsonProperty(URL_FIELD)
    private String url;
    /**
     * True, if a custom certificate was provided for webhook certificate checks
     */
    @JsonProperty(HASCUSTOMCERTIFICATE_FIELD)
    private Boolean hasCustomCertificate;
    /**
     * Number updates awaiting delivery
     */
    @JsonProperty(PENDINGUPDATECOUNT_FIELD)
    private Integer pendingUpdatesCount;
    /**
     * Optional.
     * Unix time for the most recent error that happened when trying to deliver an update via webhook
     */
    @JsonProperty(LASTERRORDATE_FIELD)
    private Integer lastErrorDate;
    /**
     * Optional.
     * Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook
     */
    @JsonProperty(LASTERRORMESSAGE_FIELD)
    private String lastErrorMessage;
    /**
     * Optional.
     * Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
     */
    @JsonProperty(MAXCONNECTIONS_FIELD)
    private Integer maxConnections;
    /**
     * Optional.
     * A list of update types the bot is subscribed to. Defaults to all update types
     */
    @JsonProperty(ALLOWEDUPDATES_FIELD)
    private List<String> allowedUpdates;
    /**
     * Optional.
     * Currently used webhook IP address
     */
    @JsonProperty(IPADDRESS_FIELD)
    private String ipAddress;
    /**
     * Optional.
     * Unix time of the most recent error that happened when trying to synchronize available updates with Telegram datacenters
     */
    @JsonProperty(LASTSYNCHRONIZATIONERRORDATE_FIELD)
    private Integer lastSynchronizationErrorDate;
}
