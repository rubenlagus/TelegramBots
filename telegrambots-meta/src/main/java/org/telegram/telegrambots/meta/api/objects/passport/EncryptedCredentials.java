package org.telegram.telegrambots.meta.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Contains data required for decrypting and authenticating EncryptedPassportElement.
 * See the Telegram Passport Documentation for a complete description of the data decryption and authentication processes.
 */
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

    public EncryptedCredentials() {
    }

    public EncryptedCredentials(String data, String hash, String secret) {
        this.data = data;
        this.hash = hash;
        this.secret = secret;
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "EncryptedCredentials{" +
                "data='" + data + '\'' +
                ", hash='" + hash + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
