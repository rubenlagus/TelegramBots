package org.telegram.telegrambots.meta.api.methods.updates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 6.1
 * Use this method to specify a URL and receive incoming updates via an outgoing webhook.
 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified URL,
 * containing a JSON-serialized Update. In case of an unsuccessful request,
 * we will give up after a reasonable amount of attempts. Returns True on success.
 * If you'd like to make sure that the webhook was set by you, you can specify secret data in the parameter secret_token.
 * If specified, the request will contain a header “X-Telegram-Bot-Api-Secret-Token” with the secret token as content.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetWebhook extends PartialBotApiMethod<Boolean> {
    public static final String PATH = "setWebhook";

    public static final String URL_FIELD = "url";
    public static final String CERTIFICATE_FIELD = "certificate";
    public static final String MAX_CONNECTIONS_FIELD = "max_connections";
    public static final String ALLOWED_UPDATES_FIELD = "allowed_updates";
    public static final String IP_ADDRESS_FIELD = "ip_address";
    public static final String DROP_PENDING_UPDATES_FIELD = "drop_pending_updates";
    public static final String SECRET_TOKEN_FIELD = "secret_token";

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
    @JsonProperty(MAX_CONNECTIONS_FIELD)
    private Integer maxConnections;
    /**
     * Optional
     * A JSON-serialized list of the update types you want your bot to receive.
     * For example, specify ["message", "edited_channel_post", "callback_query"] to only receive updates of these types.
     * See Update for a complete list of available update types.
     * Specify an empty list to receive all update types except chat_member, message_reaction, and message_reaction_count (default).
     * If not specified, the previous setting will be used.
     * Please note that this parameter doesn't affect updates created before the call to the setWebhook,
     * so unwanted updates may be received for a short period of time.
     */
    @JsonProperty(ALLOWED_UPDATES_FIELD)
    @Singular
    private List<String> allowedUpdates;
    @JsonProperty(IP_ADDRESS_FIELD)
    private String ipAddress;
    @JsonProperty(DROP_PENDING_UPDATES_FIELD)
    private Boolean dropPendingUpdates;
    /**
     * Optional
     * A secret token to be sent in a header “X-Telegram-Bot-Api-Secret-Token” in every webhook request, 1-256 characters.
     * Only characters A-Z, a-z, 0-9, _ and - are allowed.
     * The header is useful to ensure that the request comes from a webhook set by you.
     */
    @JsonProperty(SECRET_TOKEN_FIELD)
    private String secretToken;

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (url.isEmpty()) {
            throw new TelegramApiValidationException("URL parameter can't be empty", this);
        }
        if (certificate != null) {
            if (!certificate.isNew()) {
                throw new TelegramApiValidationException("Certificate parameter must be a new file to upload", this);
            }
        }
        if (secretToken != null && !secretToken.matches("[A-Za-z0-9_-]+")) {
            throw new TelegramApiValidationException("SecretToken parameter must only contains A-Z, a-z, 0-9, _ and -", this);
        }
    }
}
