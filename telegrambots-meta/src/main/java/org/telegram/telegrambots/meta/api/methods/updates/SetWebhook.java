package org.telegram.telegrambots.meta.api.methods.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to specify a url and receive incoming updates via an outgoing webhook.
 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url,
 * containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a
 * reasonable amount of attempts.
 * 20 of June of 2015
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetWebhook extends BotApiMethod<Boolean> {
    public static final String PATH = "setWebhook";

    public static final String URL_FIELD = "url";
    public static final String CERTIFICATE_FIELD = "certificate";
    public static final String MAXCONNECTIONS_FIELD = "max_connections";
    public static final String ALLOWEDUPDATES_FIELD = "allowed_updates";
    public static final String IPADDRESS_FIELD = "ip_address";
    public static final String DROPPENDINGUPDATES_FIELD = "drop_pending_updates";

    @JsonProperty(URL_FIELD)
    @NonNull
    private String url; ///< HTTPS url to send updates to. Use an empty string to remove webhook integration
    @JsonProperty(CERTIFICATE_FIELD)
    private InputFile certificate; ///< Optional. Upload your public key certificate so that the root certificate in use can be checked
    /**
     * Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100.
     * Defaults to 40. Use lower values to limit the load on your bot‘s server,
     * and higher values to increase your bot’s throughput.
     */
    @JsonProperty(MAXCONNECTIONS_FIELD)
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
    @JsonProperty(ALLOWEDUPDATES_FIELD)
    @Singular
    private List<String> allowedUpdates;
    @JsonProperty(IPADDRESS_FIELD)
    private String ipAddress;
    @JsonProperty(DROPPENDINGUPDATES_FIELD)
    private Boolean dropPendingUpdates;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>(){});
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting webhook", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url == null || url.isEmpty()) {
            throw new TelegramApiValidationException("URL parameter can't be empty", this);
        }
        if (certificate != null) {
            if (!certificate.isNew()) {
                throw new TelegramApiValidationException("Certificate parameter must be a new file to upload", this);
            }
        }
    }
}
