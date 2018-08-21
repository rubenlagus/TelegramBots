package org.telegram.telegrambots.meta.api.objects.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Contains information about documents or other Telegram Passport data shared with the bot by the user.
 */
@SuppressWarnings("unused")
public class EncryptedPassportElement implements BotApiObject {
    private static final String TYPE_FIELD = "type";
    private static final String DATA_FIELD = "data";
    private static final String PHONENUMBER_FIELD = "phone_number";
    private static final String EMAIL_FIELD = "email";
    private static final String FILES_FIELD = "files";
    private static final String FRONTSIDE_FIELD = "front_side";
    private static final String REVERSESIDE_FIELD = "reverse_side";
    private static final String SELFIE_FIELD = "selfie";
    private static final String TRANSLATION_FIELD = "translation";
    private static final String HASH_FIELD = "hash";

    /**
     * Data type. One of “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport”,
     * “address”, “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration”,
     * “temporary_registration”, “phone_number”, “email”.
     */
    @JsonProperty(TYPE_FIELD)
    private String type;
    /**
     * Optional. Base64-encoded encrypted Telegram Passport data provided by the user,
     * available for “personal_details”, “passport”, “driver_license”, “identity_card”,
     * “identity_passport” and “address” types.
     * Can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @JsonProperty(DATA_FIELD)
    private String data;
    @JsonProperty(PHONENUMBER_FIELD)
    private String phoneNumber; ///< Optional. User's verified phone number, available only for “phone_number” type
    @JsonProperty(EMAIL_FIELD)
    private String email; ///< Optional. User's verified email address, available only for “email” type
    /**
     * Optional. Array of files with encrypted documents, provided by the user, available for “utility_bill”,
     * “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types.
     * Files can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @JsonProperty(FILES_FIELD)
    private List<PassportFile> files;
    /**
     * Optional. File with encrypted document's front side, provided by the user. Available for “passport”,
     * “driver_license”, “identity_card” and “internal_passport”.
     * The file can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @JsonProperty(FRONTSIDE_FIELD)
    private PassportFile frontSide;
    /**
     * Optional. File with encrypted document's reverse side, provided by the user. Available for
     * “driver_license” and “identity_card”.
     * The file can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @JsonProperty(REVERSESIDE_FIELD)
    private PassportFile reverseSide;
    /**
     * Optional. File with encrypted selfie of the user with a document, provided by the user,
     * can be available for “passport”, “driver_license”, “identity_card” and “internal_passport”.
     * The file can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @JsonProperty(SELFIE_FIELD)
    private PassportFile selfie;
    /**
     * Optional. Array of encrypted files with translated versions of documents provided by the user.
     * Available if requested for “passport”, “driver_license”, “identity_card”, “internal_passport”,
     * “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types.
     * Files can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @JsonProperty(TRANSLATION_FIELD)
    private ArrayList<PassportFile> translations;
    /**
     * Base64-encoded element hash for using in PassportElementErrorUnspecified
     */
    @JsonProperty(HASH_FIELD)
    private String hash;

    public EncryptedPassportElement(String type, String data, String phoneNumber, String email, List<PassportFile> files,
                                    PassportFile frontSide, PassportFile reverseSide, PassportFile selfie) {
        this.type = type;
        this.data = data;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.files = files;
        this.frontSide = frontSide;
        this.reverseSide = reverseSide;
        this.selfie = selfie;
    }

    public EncryptedPassportElement() {
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public List<PassportFile> getFiles() {
        return files;
    }

    public PassportFile getFrontSide() {
        return frontSide;
    }

    public PassportFile getReverseSide() {
        return reverseSide;
    }

    public PassportFile getSelfie() {
        return selfie;
    }

    public String getHash() {
        return hash;
    }

    public boolean hasTranslations() {
        return translations != null && !translations.isEmpty();
    }

    public ArrayList<PassportFile> getTranslations() {
        return translations;
    }

    @Override
    public String toString() {
        return "EncryptedPassportElement{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", files=" + files +
                ", frontSide=" + frontSide +
                ", reverseSide=" + reverseSide +
                ", selfie=" + selfie +
                ", translations=" + translations +
                ", hash='" + hash + '\'' +
                '}';
    }
}
