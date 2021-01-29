package org.telegram.telegrambots.meta.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Contains data required for decrypting and authenticating EncryptedPassportElement.
 * See the Telegram Passport Documentation for a complete description of the data decryption and authentication processes.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EncryptedCredentials implements BotApiObject {
    private static final String DATA_FIELD = "data";
    private static final String HASH_FIELD = "hash";
    private static final String SECRET_FIELD = "secret";

    /**
     * Base64-encoded encrypted JSON-serialized data with unique user's payload,
     * data hashes and secrets required for EncryptedPassportElement decryption and authentication
     */
    @JsonProperty(DATA_FIELD)
    private String data;
    @JsonProperty(HASH_FIELD)
    private String hash; ///< Base64-encoded data hash for data authentication
    @JsonProperty(SECRET_FIELD)
    private String secret; ///< Base64-encoded secret, encrypted with the bot's public RSA key, required for data decryption
}
