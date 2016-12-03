package org.telegram.telegrambots.api.methods.updates;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to specify a url and receive incoming updates via an outgoing webhook.
 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url,
 * containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a
 * reasonable amount of attempts.
 * @date 20 of June of 2015
 */
public class SetWebhook {
    public static final String PATH = "setwebhook";

    public static final String URL_FIELD = "url";
    public static final String CERTIFICATE_FIELD = "certificate";
    public static final String MAXCONNECTIONS_FIELD = "max_connections";
    public static final String ALLOWEDUPDATES_FIELD = "allowed_updates";

    private String url; ///< Optional. HTTPS url to send updates to. Use an empty string to remove webhook integration
    private String certificateFile; ///< Optional. Upload your public key certificate so that the root certificate in use can be checked
    /**
     * Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100.
     * Defaults to 40. Use lower values to limit the load on your bot‘s server,
     * and higher values to increase your bot’s throughput.
     */
    private Integer maxConnections;
    /**
     * List the types of updates you want your bot to receive.
     * For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive
     * updates of these types. Specify an empty list to receive all updates regardless of type (default).
     * If not specified, the previous setting will be used.
     *
     * Please note that this parameter doesn't affect updates created before the call to the setWebhook,
     * so unwanted updates may be received for a short period of time.
     */
    private List<String> allowedUpdates;

    public SetWebhook() {
        this.url = "";
    }

    public String getUrl() {
        return url;
    }

    public SetWebhook setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public SetWebhook setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
        return this;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public SetWebhook setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    public List<String> getAllowedUpdates() {
        return allowedUpdates;
    }

    public SetWebhook setAllowedUpdates(List<String> allowedUpdates) {
        this.allowedUpdates = allowedUpdates;
        return this;
    }

    @Override
    public String toString() {
        return "SetWebhook{" +
                "url='" + url + '\'' +
                ", certificateFile='" + certificateFile + '\'' +
                ", maxConnections=" + maxConnections +
                ", allowedUpdates=" + allowedUpdates +
                '}';
    }
}
